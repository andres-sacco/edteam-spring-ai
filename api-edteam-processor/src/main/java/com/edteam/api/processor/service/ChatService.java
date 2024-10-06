package com.edteam.api.processor.service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

import com.edteam.api.processor.dto.*;
import com.edteam.api.processor.enums.APIError;
import com.edteam.api.processor.enums.Model;
import com.edteam.api.processor.exception.EdteamException;
import com.edteam.api.processor.repository.ProcessorHistoryRepository;
import com.edteam.api.processor.util.ProcessorUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient ollamaChatClient;
    private final ChatClient openaiChatClient;
    private final ProcessorHistoryRepository repository;

    public ChatService(
            @Qualifier(value = "ollamaChatModel") ChatModel ollamaChatClient,
            @Qualifier(value = "openAiChatModel") ChatModel openaiChatClient,
            ProcessorHistoryRepository repository) {
        InMemoryChatMemory memory = new InMemoryChatMemory();

        this.ollamaChatClient =
                ChatClient.builder(ollamaChatClient)
                        .defaultAdvisors(
                                new PromptChatMemoryAdvisor(memory),
                                new MessageChatMemoryAdvisor(memory))
                        .build();

        this.openaiChatClient =
                ChatClient.builder(openaiChatClient)
                        .defaultAdvisors(
                                new PromptChatMemoryAdvisor(memory),
                                new MessageChatMemoryAdvisor(memory))
                        .build();

        this.repository = repository;
    }

    @Cacheable(value = "processors", key = "#request.model + '-' + #request.prompt")
    public AnalysisResponseDTO queryAi(ProcessorDTO request) {
        try {
            if (request.getModel() == Model.LLAMA) {
                throw new EdteamException(APIError.VALIDATION_ERROR);
            }

            BeanOutputConverter<AnalysisResponseDTO> beanOutputConverter =
                    new BeanOutputConverter<>(AnalysisResponseDTO.class);

            String format = beanOutputConverter.getFormat();


            UserMessage userMessage = new UserMessage(request.getPrompt().concat(" with the format: ").concat(format));

            String response =
                    getChatClient(request.getModel())
                            .prompt(
                                    new Prompt(
                                            List.of(userMessage),
                                            OpenAiChatOptions.builder()
                                                    .withFunction("SalesByPeriod")
                                                    .build()))
                            .call()
                            .content();

            // Update the interaction history
            if (Objects.nonNull(request.getConversationId())) {
                repository.save(
                        new ProcessorHistoryDTO(
                                request.getConversationId(), request.getPrompt(), response));
            }
            return beanOutputConverter.convert(response);

        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    public String queryAi(ProcessorMultipartDTO request) {
        try {
            String fileContent = ProcessorUtil.convertFileToString(request.getFile());
            return askToAI(request, fileContent);
        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    public String queryAi(ProcessorFilesDTO request) {
        try {
            String fileContent = ProcessorUtil.convertFilesToString(request.getFiles());
            return askToAI(request, fileContent);
        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    @Cacheable(value = "history", key = "#conversationId")
    public List<ProcessorHistoryDTO> getHistoryByConversationId(String conversationId) {
        return repository.getHistoryByConversationId(conversationId);
    }

    private String askToAI(ProcessorDTO request, String fileContent) {

        Prompt prompt = getPrompt(request, fileContent);
        LOGGER.info(prompt.getInstructions().toString());

        String response =
                getChatClient(request.getModel())
                        .prompt()
                        .user(prompt.toString())
                        .advisors(
                                a ->
                                        a.param(
                                                        CHAT_MEMORY_CONVERSATION_ID_KEY,
                                                        request.getConversationId())
                                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                        .call()
                        .content();

        // Update the interaction history
        if (Objects.nonNull(request.getConversationId())) {
            repository.save(
                    new ProcessorHistoryDTO(
                            request.getConversationId(), request.getPrompt(), response));
        }

        return response;
    }

    private Prompt getPrompt(ProcessorDTO request, String fileContent) {
        PromptTemplate promptTemplate = getPromptTemplate();

        Map<String, Object> params =
                Map.of("fileContent", fileContent, "prompt", request.getPrompt());
        return promptTemplate.create(params);
    }

    private PromptTemplate getPromptTemplate() {
        var template =
                """
                I am an analyst of a big company which have different reports.
                I am mostly interested to analyze the files.
                Here is the file content I am analyzing:
                {fileContent}
                I want only the information {prompt} that I request it.""";

        return new PromptTemplate(template);
    }

    private ChatClient getChatClient(Model model) {
        if (model == Model.LLAMA) {
            return ollamaChatClient;

        } else if (model == Model.OPENAI) {
            return openaiChatClient;
        }
        return ollamaChatClient;
    }
}

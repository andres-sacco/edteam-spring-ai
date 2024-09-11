package com.edteam.api.processor.service;

import com.edteam.api.processor.ProcessorHistoryRepository;
import com.edteam.api.processor.connector.UserConnector;
import com.edteam.api.processor.dto.ProcessorDTO;
import com.edteam.api.processor.dto.ProcessorHistoryDTO;
import com.edteam.api.processor.enums.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient ollamaChatClient;
    private final ChatClient openaiChatClient;
    private final UserConnector connector;
    private final ProcessorHistoryRepository repository;

    public ChatService(@Qualifier(value = "ollamaChatClient")ChatClient ollamaChatClient,
                       @Qualifier(value = "openAiChatClient")ChatClient openaiChatClient,
                       UserConnector connector,
                       ProcessorHistoryRepository repository) {
        this.ollamaChatClient = ollamaChatClient;
        this.openaiChatClient = openaiChatClient;
        this.connector = connector;
        this.repository = repository;
    }

    public String queryAi(ProcessorDTO request) {
        //Call to the other API
        //Validate the security

        try {
            return askToAI(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProcessorHistoryDTO> getHistoryByUserId(String userId) {
        return repository.getHistoryByUser(userId);
    }


    private String askToAI(ProcessorDTO request) throws IOException {

        String fileContent = "";
        for (String file: request.getFiles()) {
            ClassPathResource resource = new ClassPathResource(file);
            Path filePath = resource.getFile().toPath();
            fileContent = fileContent.concat(Files.readString(filePath).concat("\r\n"));
        }

        Prompt prompt = getPrompt(request, fileContent);
        LOGGER.info(prompt.getInstructions().toString());

        String response = getChatClient(request.getModel()).call(prompt).getResult().getOutput().getContent();

        // Update the interaction history
        repository.save(new ProcessorHistoryDTO(request.getUserId(), request.getPrompt(), response));
        return response;
    }

    private Prompt getPrompt(ProcessorDTO request, String fileContent) {
        if(request.getHistory()) {
            PromptTemplate promptTemplate = getPromptTemplateWithHistory();

            List<String> interactionHistory = new ArrayList<>();

            List<ProcessorHistoryDTO> histories = repository.getHistoryByUser(request.getUserId());
            for (ProcessorHistoryDTO dto: histories) {
                interactionHistory.add("User: " + dto.getPrompt());
                interactionHistory.add("AI: " + dto.getResponse());
            }

            // Add historical context to the prompt
            String history = String.join("\n", interactionHistory);

            Map<String, Object> params = Map.of("history", history,"fileContent", fileContent, "prompt", request.getPrompt());
            return promptTemplate.create(params);
        } else {
            PromptTemplate promptTemplate = getPromptTemplateWithoutHistory();

            Map<String, Object> params = Map.of("fileContent", fileContent, "prompt", request.getPrompt());
            return promptTemplate.create(params);
        }
    }

    private PromptTemplate getPromptTemplateWithoutHistory() {
        var template = """
                I am an analyst of a big company which have different reports.
                I am mostly interested to analyze the files.
                Here is the file content I am analyzing:
                {fileContent}
                I want only the information {prompt} that I request it.""";

        return new PromptTemplate(template);
    }

    private PromptTemplate getPromptTemplateWithHistory() {
        var template = """
                Previous interactions:
                {history}
                
                I am an analyst of a big company which have different reports.
                I am mostly interested to analyze the files.
                Here is the file content I am analyzing:
                {fileContent}
                I want only the information {prompt} that I request it.""";

        return new PromptTemplate(template);
    }

    private ChatClient getChatClient(Model model) {
        if(model == Model.LLAMA) {
            return ollamaChatClient;

        } else if(model == Model.OPENAI) {
            return openaiChatClient;
        }



        ollamaChatClient.
        return ollamaChatClient;
    }


}

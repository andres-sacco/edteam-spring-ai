package com.edteam.processor.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatService {


    private final ChatClient ollamaChatClient;
    private final ChatClient openaiChatClient;


    public ChatService(@Qualifier(value = "ollamaChatClient")ChatClient ollamaChatClient,
                       @Qualifier(value = "openAiChatClient")ChatClient openaiChatClient) {
        this.ollamaChatClient = ollamaChatClient;
        this.openaiChatClient = openaiChatClient;
    }

    public String queryAi(String prompt, String model) {
        return getChatClient(model).call(prompt);
    }

    public String getCityGuide(String city, String interest, String model) {
        var template = """
                I am a tourist visiting the city of {city}.
                I am mostly interested in {interest}.
                Tell me tips on what to do there.""";

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Map<String, Object> params = Map.of("city", city, "interest", interest);
        Prompt prompt = promptTemplate.create(params);

        return getChatClient(model).call(prompt).getResult().getOutput().getContent();
    }

    private ChatClient getChatClient(String model) {
        if(model.equals("ollama")) {
            return ollamaChatClient;

        } else if(model.equals("openai")) {
            return openaiChatClient;
        }

        return ollamaChatClient;
    }

}

package com.edteam.api.processor.dto;


import java.time.LocalDateTime;

public class ProcessorHistoryDTO {
    private String conversationId;
    private LocalDateTime date;
    private String prompt;
    private String response;

    public ProcessorHistoryDTO(String conversationId, String prompt, String response) {
        this.conversationId = conversationId;
        this.date = LocalDateTime.now();
        this.prompt = prompt;
        this.response = response;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getResponse() {
        return response;
    }

    public String getConversationId() {
        return conversationId;
    }
}

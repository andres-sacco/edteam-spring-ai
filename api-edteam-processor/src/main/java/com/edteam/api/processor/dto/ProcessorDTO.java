package com.edteam.api.processor.dto;

import com.edteam.api.processor.enums.Model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProcessorDTO {

    @NotNull(message = "The model must be defined")
    private Model model;

    private String conversationId;

    @NotBlank(message = "The prompt must be defined")
    private String prompt;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "ProcessorRequestDTO{"
                + "model='"
                + model
                + '\''
                + ", conversationId='"
                + conversationId
                + '\''
                + ", prompt='"
                + prompt
                + '\''
                + '}';
    }
}

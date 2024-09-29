package com.edteam.api.processor.dto;

import com.edteam.api.processor.enums.Model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessorDTO that = (ProcessorDTO) o;
        return model == that.model && Objects.equals(conversationId, that.conversationId) && Objects.equals(prompt, that.prompt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, conversationId, prompt);
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

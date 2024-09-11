package com.edteam.api.processor.dto;

import com.edteam.api.processor.enums.Model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProcessorDTO {

    @NotNull(message = "The model must be defined")
    private Model model;

    @Valid
    @NotEmpty(message = "You need at least one file")
    private List<String> files;

    @NotBlank(message = "The userId must be defined")
    private String userId;

    @NotBlank(message = "The prompt must be defined")
    private String prompt;

    @NotNull(message = "The history must be defined")
    private Boolean history;


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "ProcessorRequestDTO{" +
                "model='" + model + '\'' +
                ", files=" + files +
                ", userId='" + userId + '\'' +
                ", prompt='" + prompt + '\'' +
                ", history=" + history +
                '}';
    }
}

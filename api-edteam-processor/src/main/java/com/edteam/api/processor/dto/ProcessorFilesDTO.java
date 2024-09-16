package com.edteam.api.processor.dto;

import jakarta.validation.constraints.NotNull;

public class ProcessorFilesDTO extends ProcessorDTO {

    @NotNull(message = "You need one file")
    private String[] files;

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }
}

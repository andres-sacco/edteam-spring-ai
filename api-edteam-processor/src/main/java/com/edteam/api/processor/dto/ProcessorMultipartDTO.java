package com.edteam.api.processor.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ProcessorMultipartDTO extends ProcessorDTO {

    @NotNull(message = "You need one file")
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

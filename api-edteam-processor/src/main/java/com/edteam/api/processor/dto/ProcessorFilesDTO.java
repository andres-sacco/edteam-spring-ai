package com.edteam.api.processor.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class ProcessorFilesDTO extends ProcessorDTO {

    @NotNull(message = "You need one file")
    private String[] files;

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProcessorFilesDTO that = (ProcessorFilesDTO) o;
        return Objects.deepEquals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(files));
    }
}

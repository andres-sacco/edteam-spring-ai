package com.edteam.api.processor.dto;

import java.util.List;

public class DataResponseDTO {

    private String name;
    private String description;
    private List<ValueResponseDTO> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ValueResponseDTO> getValues() {
        return values;
    }

    public void setValues(List<ValueResponseDTO> values) {
        this.values = values;
    }
}

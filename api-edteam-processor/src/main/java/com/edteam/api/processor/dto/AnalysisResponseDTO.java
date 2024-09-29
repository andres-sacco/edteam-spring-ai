package com.edteam.api.processor.dto;

public class AnalysisResponseDTO {

    private String response;
    private String analysis;
    private DataResponseDTO data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public DataResponseDTO getData() {
        return data;
    }

    public void setData(DataResponseDTO data) {
        this.data = data;
    }
}

package com.edteam.api.processor.service;

import com.edteam.api.processor.dto.*;
import com.edteam.api.processor.enums.APIError;
import com.edteam.api.processor.enums.Model;
import com.edteam.api.processor.exception.EdteamException;
import com.edteam.api.processor.repository.ProcessorHistoryRepository;
import com.edteam.api.processor.util.ProcessorUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ProcessorHistoryRepository repository;

    public ChatService(
            ProcessorHistoryRepository repository) {
        this.repository = repository;
    }

    public AnalysisResponseDTO queryAi(ProcessorDTO request) {
        try {
            if (request.getModel() == Model.LLAMA) {
                throw new EdteamException(APIError.VALIDATION_ERROR);
            }
            return new AnalysisResponseDTO();
        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    public String queryAi(ProcessorMultipartDTO request) {
        try {
            String fileContent = ProcessorUtil.convertFileToString(request.getFile());
            return askToAI(request, fileContent);
        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    public String queryAi(ProcessorFilesDTO request) {
        try {
            String fileContent = ProcessorUtil.convertFilesToString(request.getFiles());
            return askToAI(request, fileContent);
        } catch (Exception e) {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    public List<ProcessorHistoryDTO> getHistoryByConversationId(String conversationId) {
        return repository.getHistoryByConversationId(conversationId);
    }

    private String askToAI(ProcessorDTO request, String fileContent) {
        String response = "";

        return response;
    }

}

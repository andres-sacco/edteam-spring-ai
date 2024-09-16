package com.edteam.api.processor.repository;

import com.edteam.api.processor.dto.ProcessorHistoryDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProcessorHistoryRepository {

    static HashMap<String, List<ProcessorHistoryDTO>> histories = new HashMap<>();

    public List<ProcessorHistoryDTO> getHistoryByConversationId(String conversationId) {
        List<ProcessorHistoryDTO> results = histories.get(conversationId);
        if (results == null) {
            results = new ArrayList<>();
        }
        return results;
    }

    public void save(ProcessorHistoryDTO processorHistory) {
        List<ProcessorHistoryDTO> results = histories.get(processorHistory.getConversationId());
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(processorHistory);
        histories.put(processorHistory.getConversationId(), results);
    }
}

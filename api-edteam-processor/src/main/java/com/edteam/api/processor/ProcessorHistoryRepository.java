package com.edteam.api.processor;

import com.edteam.api.processor.dto.ProcessorHistoryDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProcessorHistoryRepository {

    static HashMap<String, List<ProcessorHistoryDTO>> histories = new HashMap<>();

    public List<ProcessorHistoryDTO> getHistoryByUser(String userId) {
        List<ProcessorHistoryDTO> results = histories.get(userId);
        if(results == null) {
            results = new ArrayList<>();
        }
        return results;
    }

    public void save(ProcessorHistoryDTO processorHistory) {
        List<ProcessorHistoryDTO> results = histories.get(processorHistory.getUserId());
        if(results == null) {
            results = new ArrayList<>();
        }
        results.add(processorHistory);
        histories.put(processorHistory.getUserId(), results);
    }
}

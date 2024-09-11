package com.edteam.api.processor.controller;

import com.edteam.api.processor.controller.resource.ProcessorHistoryResource;
import com.edteam.api.processor.dto.ProcessorHistoryDTO;
import com.edteam.api.processor.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProcessorHistoryController implements ProcessorHistoryResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorHistoryController.class);

    private final ChatService chatService;

    public ProcessorHistoryController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/history/{userId}")
    @Override
    public ResponseEntity<List<ProcessorHistoryDTO>> getHistory(@PathVariable String userId) {
        LOGGER.info("Processing history of user {}", userId);

        List<ProcessorHistoryDTO> response = chatService.getHistoryByUserId(userId);
        return ResponseEntity.ok().body(response);
    }
}

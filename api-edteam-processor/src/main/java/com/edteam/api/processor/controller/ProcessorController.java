package com.edteam.api.processor.controller;

import com.edteam.api.processor.controller.resource.ProcessorResource;
import com.edteam.api.processor.dto.ProcessorDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.edteam.api.processor.service.ChatService;

@Validated
@RestController
public class ProcessorController implements ProcessorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorController.class);

    private final ChatService chatService;

    public ProcessorController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    @PostMapping(value = "/chat")
    public ResponseEntity<String> askAi(@RequestBody @Valid ProcessorDTO request) {
        LOGGER.info("Processing the files with {}", request.toString());

        String response = chatService.queryAi(request);
        return ResponseEntity.ok().body(response);
    }
}

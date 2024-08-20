package com.edteam.processor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edteam.processor.service.ChatService;

@RestController
public class AiController {

    private final ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("ask-ai")
    public String askAi(@RequestParam("prompt") String prompt, @RequestParam("model") String model){
        return chatService.queryAi(prompt, model);
    }


    @GetMapping("city-guide")
    public String cityGuide(@RequestParam("city") String city, @RequestParam("interest") String interest, @RequestParam("model") String model) {
        return chatService.getCityGuide(city, interest, model);
    }
}

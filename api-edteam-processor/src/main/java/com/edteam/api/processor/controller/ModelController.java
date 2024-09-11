package com.edteam.api.processor.controller;

import com.edteam.api.processor.controller.resource.ModelResource;
import com.edteam.api.processor.enums.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ModelController implements ModelResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);


    @Override
    @GetMapping("/model")
    public ResponseEntity<List<String>> getModels() {
        LOGGER.info("Get available models");
        List<String> models = new ArrayList<>();

        for (Model model : Model.values()) {
            models.add(model.toString());
        }
        return ResponseEntity.ok().body(models);
    }
}

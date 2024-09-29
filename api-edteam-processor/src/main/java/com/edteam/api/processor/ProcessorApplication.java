package com.edteam.api.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ProcessorApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProcessorApplication.class, args);
    }
}

package com.edteam.api.processor.configuration;

import com.edteam.api.processor.connector.SaleConnector;
import com.edteam.api.processor.service.SalesService;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfiguration {

    @Autowired
    SaleConnector saleConnector;

    @Bean
    public FunctionCallback salesFunctionInfo() {

        return FunctionCallbackWrapper.builder(new SalesService(saleConnector))
                .withName("SalesByPeriod") // (1) function name
                .withDescription("Get the sales by period") // (2) function description
                .build();
    }
}

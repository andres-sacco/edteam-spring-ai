package com.edteam.api.processor.service;

import com.edteam.api.processor.connector.SaleConnector;
import com.edteam.api.processor.connector.response.SaleDTO;

import java.util.List;
import java.util.function.Function;


public class SalesService implements Function<SalesService.Request, SalesService.Response> {

    SaleConnector connector;

    public record Request(String from, String to, String kind) {}
    public record Response(List<SaleDTO> sales) {}

    public SalesService(SaleConnector connector) {
        this.connector = connector;
    }

    public Response apply(Request request) {
        return new Response(connector.getSales(request.from(), request.to(), request.kind()));
    }
}
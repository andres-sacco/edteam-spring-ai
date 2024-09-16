package com.edteam.api.processor.connector;

import com.edteam.api.processor.connector.configuration.EndpointConfiguration;
import com.edteam.api.processor.connector.configuration.HostConfiguration;
import com.edteam.api.processor.connector.configuration.HttpConnectorConfiguration;
import com.edteam.api.processor.connector.response.SaleDTO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class SaleConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleConnector.class);
    private final String HOST = "api-sales";

    private final String ENDPOINT = "get-sales-info";

    private HttpConnectorConfiguration configuration;

    @Autowired
    public SaleConnector(HttpConnectorConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<SaleDTO> getSales(String from, String to, String kind) {
        LOGGER.info("calling to api-sales");

        System.out.println(from);
        System.out.println(to);

        HostConfiguration hostConfiguration = configuration.getHosts().get(HOST);
        EndpointConfiguration endpointConfiguration =
                hostConfiguration.getEndpoints().get(ENDPOINT);

        HttpClient httpClient =
                HttpClient.create()
                        .option(
                                ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                Math.toIntExact(endpointConfiguration.getConnectionTimeout()))
                        .doOnConnected(
                                conn ->
                                        conn.addHandler(
                                                        new ReadTimeoutHandler(
                                                                endpointConfiguration
                                                                        .getReadTimeout(),
                                                                TimeUnit.MILLISECONDS))
                                                .addHandler(
                                                        new WriteTimeoutHandler(
                                                                endpointConfiguration
                                                                        .getWriteTimeout(),
                                                                TimeUnit.MILLISECONDS)));

        WebClient client =
                WebClient.builder()
                        .baseUrl(
                                "https://"
                                        + hostConfiguration.getHost()
                                        + endpointConfiguration.getUrl())
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(
                                HttpHeaders.AUTHORIZATION,
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjhiNWUwZmI4LWY5NTQtNDA2Zi04MDhjLTEyYjBlNTRjZGM2NyIsImVtYWlsIjoiYWRtaW5AZWQudGVhbSIsImlzcyI6IkVEdGVhbSIsImV4cCI6MTcyNjA5MzM3MX0.DMq7y--1lrB_7I3iqFtB11trUeybBvcb9FViWj6XThE")
                        // .defaultHeader(HttpHeaders.AUTHORIZATION,
                        // SecurityContextHolder.getContext().getAuthentication().getCredentials().toString())
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();

        return client.get()
                .uri(urlEncoder -> urlEncoder.queryParam(from, to, kind).build())
                .retrieve()
                .bodyToMono(List.class)
                .share()
                .block();
    }
}

package com.edteam.api.processor.connector;

import com.edteam.api.processor.connector.configuration.EndpointConfiguration;
import com.edteam.api.processor.connector.configuration.HostConfiguration;
import com.edteam.api.processor.connector.configuration.HttpConnectorConfiguration;
import com.edteam.api.processor.dto.UserDTO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
public class UserConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConnector.class);
    private final String HOST = "api-users";

    private final String ENDPOINT = "get-users-files";

    private HttpConnectorConfiguration configuration;

    @Autowired
    public UserConnector(HttpConnectorConfiguration configuration) {
        this.configuration = configuration;
    }

    public UserDTO getFiles(String code) {
        LOGGER.info("calling to api-users");

        HostConfiguration hostConfiguration = configuration.getHosts().get(HOST);
        EndpointConfiguration endpointConfiguration = hostConfiguration.getEndpoints().get(ENDPOINT);

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        Math.toIntExact(endpointConfiguration.getConnectionTimeout()))
                .doOnConnected(conn -> conn
                        .addHandler(
                                new ReadTimeoutHandler(endpointConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS))
                        .addHandler(new WriteTimeoutHandler(endpointConfiguration.getWriteTimeout(),
                                TimeUnit.MILLISECONDS)));

        WebClient client = WebClient.builder()
                .baseUrl("http://" + hostConfiguration.getHost() + ":" + hostConfiguration.getPort()
                        + endpointConfiguration.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        return client.get().uri(urlEncoder -> urlEncoder.build(code)).retrieve().bodyToMono(UserDTO.class).share()
                .block();
    }
}

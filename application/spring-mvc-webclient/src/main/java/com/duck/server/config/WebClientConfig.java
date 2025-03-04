package com.duck.server.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Slf4j
@Configuration
public class WebClientConfig {

    private static final int DEFAULT_TIMEOUT = 510;
    private static final int MAX_CONNECTIONS = 500; // 최대 연결 수
    private static final int MAX_IDLE_TIME = 60; // 최대 유휴 시간 (초)
    private static final int MAX_LIFE_TIME = 300; // 연결 최대 수명 (초)

    @Bean
    public WebClient webClient() {
        log.info("WebClientInit Start");

        ConnectionProvider provider = ConnectionProvider.builder("spring-mvc")
                .maxConnections(MAX_CONNECTIONS)
                .maxIdleTime(Duration.ofSeconds(MAX_IDLE_TIME))
                .maxLifeTime(Duration.ofSeconds(MAX_LIFE_TIME))
                .evictInBackground(Duration.ofSeconds(10))
                .pendingAcquireTimeout(Duration.ofSeconds(10))
                .build();

        final HttpClient httpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIMEOUT)
                .responseTimeout(Duration.ofMillis(DEFAULT_TIMEOUT))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(DEFAULT_TIMEOUT))
                                .addHandlerLast(new WriteTimeoutHandler(DEFAULT_TIMEOUT)))
                ;

        final ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        final WebClient webClient = WebClient.builder()
                .clientConnector(connector)
                .build();

        httpClient.warmup().block();

        log.info("WebClientInit End");

        return webClient;
    }
}

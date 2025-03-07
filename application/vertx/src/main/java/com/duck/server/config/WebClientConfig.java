package com.duck.server.config;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    public static final int DEFAULT_TIMEOUT = 510;
    private static final int MAX_CONNECTIONS = 500; // 최대 연결 수
    private static final int CONNECTION_TIMEOUT = 200;
    private static final int MAX_IDLE_TIME = 10; // 최대 유휴 시간

    @Bean
    public WebClient webClient(Vertx vertx) {
        WebClientOptions options = new WebClientOptions()
                .setMaxPoolSize(MAX_CONNECTIONS)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setIdleTimeout(MAX_IDLE_TIME)
                .setIdleTimeoutUnit(TimeUnit.SECONDS);

        return WebClient.create(vertx, options);
    }
}

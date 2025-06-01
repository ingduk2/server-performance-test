package com.duck.external.config;

import com.duck.external.vertx.VertxWebVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class VertxVerticleLauncher {

    @Value("${server.port}")
    private int port;

    private final Vertx vertx;
    private final Router router;
    private final ApplicationContext applicationContext;

    @EventListener(ApplicationReadyEvent.class)
    public void deployVerticle() {
        VertxWebVerticle vertxWebVerticle = new VertxWebVerticle(router, port, applicationContext);
        vertx.deployVerticle(vertxWebVerticle);
    }
}

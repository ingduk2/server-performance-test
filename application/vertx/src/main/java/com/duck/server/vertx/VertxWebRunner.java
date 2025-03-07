package com.duck.server.vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VertxWebRunner implements CommandLineRunner {

    @Value("${server.port}")
    private int port;
    private final Vertx vertx;
    private final Router router;

    @Override
    public void run(String... args) throws Exception {
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port, http -> {
                    if (http.succeeded()) {
                        log.info("Vert.x server started on port {}", port);
                    } else {
                        log.info("Failed to start Vert.x server");
                    }
                });
    }
}

package com.duck.external.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
public class VertxWebVerticle extends AbstractVerticle {

    private final Router router;
    private final int port;
    private final ApplicationContext applicationContext;

    public VertxWebVerticle(Router router, int port, ApplicationContext applicationContext) {
        this.router = router;
        this.port = port;
        this.applicationContext = applicationContext;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HttpServer httpServer = vertx.createHttpServer()
                .requestHandler(router);

        httpServer
                .listen(port)
                .onSuccess(server -> {
                    startPromise.tryComplete();
                    log.info("Vert.x Server started on port : {}", port);
                })
                .onFailure(throwable -> {
                    startPromise.tryFail(throwable.getCause());
                    log.error("Vert.x Server Fail, ", throwable);
                    SpringApplication.exit(applicationContext, () -> 1);
                });
    }
}

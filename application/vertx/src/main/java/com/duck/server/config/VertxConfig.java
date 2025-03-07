package com.duck.server.config;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.duck.server.vertx.ControllerResource;

import java.util.List;

@Configuration
public class VertxConfig {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public Router router(
            Vertx vertx,
            List<ControllerResource> resources
    ) {
        Router router = Router.router(vertx);

        resources.forEach(resource ->
                resource.endpoints().forEach(endpoint ->
                        router.route(endpoint.getMethod(), endpoint.getPath()).handler(resource)));

        return router;
    }
}

package com.duck.server.config;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.PrometheusScrapingHandler;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.duck.server.vertx.ControllerResource;

import java.util.List;

@Configuration
public class VertxConfig {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx(new VertxOptions().setMetricsOptions(
                new MicrometerMetricsOptions()
                        .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
                        .setEnabled(true)
        ));
    }

    @Bean
    public Router router(
            Vertx vertx,
            List<ControllerResource> resources
    ) {
        // prometheus router
        Router router = Router.router(vertx);
        router.route("/metrics").handler(PrometheusScrapingHandler.create());

        // ControllerResource router
        resources.forEach(resource ->
                resource.endpoints().forEach(endpoint ->
                        router.route(endpoint.getMethod(), endpoint.getPath()).handler(resource)));

        return router;
    }
}

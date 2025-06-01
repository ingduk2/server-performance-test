package com.duck.external.config;

import com.duck.common.api.ApiResponse;
import com.duck.external.vertx.ControllerResource;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.PrometheusScrapingHandler;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        Router router = Router.router(vertx);

        // prometheus router
        router.route("/metrics").handler(PrometheusScrapingHandler.create());

        // ControllerResource router
        resources.forEach(resource ->
                resource.endpoints().forEach(endpoint ->
                        router.route(endpoint.getMethod(), endpoint.getPath()).handler(resource)));

        // Global failHandler
        router.route().failureHandler(ctx -> {
            Throwable failure = ctx.failure();
            int statusCode = ctx.statusCode() >= 0 ? ctx.statusCode() : 500;

            String message = failure != null ? failure.getMessage() : "Unknown error";
            String apiResponse = Json.encode(ApiResponse.fail(message));

            ctx.response()
                    .setStatusCode(statusCode)
                    .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .end(apiResponse);
        });

        return router;
    }
}

package com.duck.external.api;

import com.duck.common.api.ApiResponse;
import com.duck.external.vertx.ControllerResource;
import com.duck.external.vertx.HttpEndpoint;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiController implements ControllerResource {

    @Override
    public List<HttpEndpoint> endpoints() {
        return Collections.singletonList(HttpEndpoint.of(HttpMethod.GET, "/api/external"));
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String sleepParam = routingContext.request().getParam("sleepMillis");
        long sleepMillis = sleepParam != null ? Long.parseLong(sleepParam) : 1;

        routingContext.vertx().setTimer(sleepMillis, timerId -> {
            try {
                ApiResponse apiResponse = ApiResponse.success("sleep %sms".formatted(sleepMillis));
                String jsonResponse = Json.encode(apiResponse);

                routingContext.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(jsonResponse);
            } catch (Exception e) {
                routingContext.fail(e);
            }
        });
    }
}

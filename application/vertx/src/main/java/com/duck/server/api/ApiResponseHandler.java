package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApiResponseHandler {

    public void handle(RoutingContext routingContext, AsyncResult<List<ApiResponse>> context) {
        String response = Json.encode(context.result());
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(response);
    }
}

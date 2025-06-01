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
        try {
            if (context.succeeded()) {
                String response = Json.encode(context.result());
                routingContext.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(response);
            } else {
                log.error("Internal Server Error : ", context.cause());
                sendFailResponse(routingContext, 500, "Internal Server Error");
            }
        } catch (Exception e) {
            log.error("Exception : ", e);
            sendFailResponse(routingContext, 500, e.getMessage());
        }
    }

    private void sendFailResponse(RoutingContext routingContext, int statusCode, String message) {
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setStatusCode(statusCode)
                .end(Json.encode(ApiResponse.fail(message)));
    }
}

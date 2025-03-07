package com.duck.server.api;

import com.duck.server.vertx.ControllerResource;
import com.duck.server.vertx.HttpEndpoint;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiController implements ControllerResource {

    private final ApiService apiService;
    private final ApiHttpClient apiHttpClient;
    private final ApiResponseHandler apiResponseHandler;

    @Override
    public List<HttpEndpoint> endpoints() {
        return Collections.singletonList(HttpEndpoint.of(HttpMethod.GET, "/api/test"));
    }

    @Override
    public void handle(RoutingContext routingContext) {
        apiService.getServerUrls()
                .compose(apiHttpClient::request)
                .onComplete(asyncResult -> apiResponseHandler.handle(routingContext, asyncResult));
    }
}

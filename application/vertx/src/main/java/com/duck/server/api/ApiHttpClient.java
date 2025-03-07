package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import com.duck.server.config.WebClientConfig;
import io.vertx.core.Future;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiHttpClient {

    private final WebClient webClient;

    public Future<List<ApiResponse>> request(List<String> serverUrls) {
        List<Future<ApiResponse>> futures = serverUrls.stream()
                .map(this::sendRequest)
                .toList();

        return Future.all(futures).map(v ->
                futures.stream()
                        .map(Future::result)
                        .toList()
        );
    }

    private Future<ApiResponse> sendRequest(String url) {
        return webClient.getAbs(url)
                .timeout(WebClientConfig.DEFAULT_TIMEOUT)
                .as(BodyCodec.json(ApiResponse.class))
                .send()
                .onSuccess(res -> {})
                .map(HttpResponse::body)
                .recover(error -> {
                    log.error("ApiRequest Error :", error);
                    return Future.succeededFuture(ApiResponse.fail(error.getMessage()));
                });
    }
}

package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final ExternalProperties externalDomains;
    private final ApiHttpClient apiHttpClient;

    public Flux<ApiResponse> request() {
        return apiHttpClient.request(externalDomains.getUrls())
                .onErrorResume(error -> {
                    log.error("Error in ApiServices: ", error);
                    return Flux.empty();
                });
    }
}

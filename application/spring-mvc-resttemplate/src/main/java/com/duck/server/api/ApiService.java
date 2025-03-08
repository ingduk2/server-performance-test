package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final ExternalProperties externalProperties;
    private final ApiHttpClient apiHttpClient;

    public List<ApiResponse> request() {
        List<ApiResponse> result = new CopyOnWriteArrayList<>();
        List<CompletableFuture<Void>> completableFutures = new CopyOnWriteArrayList<>();

        List<String> urls = externalProperties.getUrls();
        for (String url : urls) {
            completableFutures.add(apiHttpClient.request(result, url));
        }

        try {
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                    .orTimeout(510, TimeUnit.MILLISECONDS)
                    .join();
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
        }

        return result;
    }
}

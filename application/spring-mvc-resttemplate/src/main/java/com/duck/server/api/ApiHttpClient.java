package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import com.duck.server.config.RestTemplateConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiHttpClient {

    private final RestTemplate restTemplate;

    @Async
    public CompletableFuture<Void> request(List<ApiResponse> result, String url) {
        try {
            setRestTemplateFactory();

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<ApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, ApiResponse.class);

            result.add(response.getBody());
        } catch (Exception e) {
            log.error("http Error: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }

        return CompletableFuture.completedFuture(null);
    }

    private void setRestTemplateFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(RestTemplateConfig.bidRequestTimeoutMillis);
        factory.setConnectTimeout(RestTemplateConfig.bidRequestTimeoutMillis);
        factory.setReadTimeout(RestTemplateConfig.bidRequestTimeoutMillis);
        restTemplate.setRequestFactory(factory);
    }
}

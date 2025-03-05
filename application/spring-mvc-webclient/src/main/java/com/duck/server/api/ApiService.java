package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final ExternalProperties externalProperties;
    private final ApiHttpClient apiHttpClient;

    public List<ApiResponse> request() {
        try {
            return apiHttpClient.request(externalProperties.getUrls());
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}

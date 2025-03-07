package com.duck.server.api;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final ExternalProperties externalProperties;

    public Future<List<String>> getServerUrls() {
        return Future.succeededFuture(externalProperties.getUrls());
    }
}

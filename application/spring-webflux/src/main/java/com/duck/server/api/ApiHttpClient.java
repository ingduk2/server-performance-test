package com.duck.server.api;

import com.duck.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiHttpClient {

    private final WebClient webClient;

    public Flux<ApiResponse> request(List<String> serverUrls) {
        return Flux.fromIterable(serverUrls)
                .parallel(serverUrls.size())
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::createPublisher)
                .sequential();
    }

    private Mono<ApiResponse> createPublisher(String serverUrl) {
        return webClient.get()
                .uri(serverUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .doOnError(error -> log.error("error :", error.getCause()))
                .onErrorResume(error -> {
                    String errorMessage = Optional.ofNullable(error.getCause())
                            .map(Throwable::toString)
                            .orElse(error.toString());
                    return Mono.just(ApiResponse.fail(errorMessage));
                });
    }
}

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

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiHttpClient {

    private final WebClient webClient;

    public List<ApiResponse> request(List<String> serverUrls) {
        List<Mono<ApiResponse>> publishers = serverUrls.stream()
                .map(this::createPublisher)
                .toList();

        return Flux.merge(publishers)
                .parallel(publishers.size())
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .collectList()
                .block();
    }

    private Mono<ApiResponse> createPublisher(String serverUrl) {
        return webClient.get()
                .uri(serverUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .doOnError(error -> log.error("webclient Error {}:", error.getCause().toString()))
                .onErrorResume(error -> Mono.just(ApiResponse.fail(error.getCause().toString())))
                ;
    }
}

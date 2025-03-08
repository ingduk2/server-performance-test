package com.duck.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfig {

    public static final int bidRequestTimeoutMillis = 510;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(bidRequestTimeoutMillis); // 커넥션 연결을 맺을때까지 걸리는 시간
        factory.setReadTimeout(bidRequestTimeoutMillis); // 연결 후 응답까지 대기시간
        restTemplate.setRequestFactory(factory);
        restTemplate.getMessageConverters().addFirst(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}

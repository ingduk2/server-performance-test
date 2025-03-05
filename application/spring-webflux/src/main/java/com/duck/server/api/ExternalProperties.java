package com.duck.server.api;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "external")
public class ExternalProperties {
    private final List<String> urls;

    public ExternalProperties(List<String> urls) {
        this.urls = urls;
    }
}

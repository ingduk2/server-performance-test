package com.duck.server.api;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "external")
public class ExternalProperties {
    private final List<String> domains;

    public ExternalProperties(List<String> domains) {
        this.domains = domains;
    }
}

package com.amrsoftware.msvc_role.infrastructure.webclient.config;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientImplConfig {
    public WebClient webClientImpl(String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}

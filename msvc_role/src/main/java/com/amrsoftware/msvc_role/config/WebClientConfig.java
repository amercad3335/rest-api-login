package com.amrsoftware.msvc_role.config;

import com.amrsoftware.msvc_role.infrastructure.webclient.config.WebClientImplConfig;
import com.amrsoftware.msvc_role.infrastructure.webclient.userwebclient.UserWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClientImplConfig webClientImplConfig() {
        return new WebClientImplConfig();
    }

    @Bean
    public UserWebClient webClient(WebClientImplConfig webClientImplConfig) {
        return new UserWebClient(webClientImplConfig);
    }
}

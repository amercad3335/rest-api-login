package com.amrsoftware.msvc_user.config;

import com.amrsoftware.msvc_user.infrastructure.webclient.config.WebClientImplConfig;
import com.amrsoftware.msvc_user.infrastructure.webclient.rolewebclient.RoleWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClientImplConfig webClientImplConfig() {
        return new WebClientImplConfig();
    }

    @Bean
    public RoleWebClient webClient(WebClientImplConfig webClientImplConfig) {
        return new RoleWebClient(webClientImplConfig);
    }
}

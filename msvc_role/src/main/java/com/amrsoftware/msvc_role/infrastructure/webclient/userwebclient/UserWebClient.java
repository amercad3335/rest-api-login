package com.amrsoftware.msvc_role.infrastructure.webclient.userwebclient;

import com.amrsoftware.msvc_role.infrastructure.webclient.config.WebClientImplConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class UserWebClient {
    private final WebClientImplConfig webClient;

    @Value("${app.userUrl}")
    private String url;

    public void deleteRoleByRoleId(Long id) {
        webClient.webClientImpl(url).delete()
            .uri(uriBuilder -> uriBuilder.path("/api/delete-role-by-id/{id}").build(id))
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}

package com.amrsoftware.msvc_user.infrastructure.webclient.rolewebclient;

import com.amrsoftware.msvc_user.domain.model.role.Role;
import com.amrsoftware.msvc_user.infrastructure.webclient.config.WebClientImplConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class RoleWebClient {
    private final WebClientImplConfig webClient;

    @Value("${app.roleUrl}")
    private String url;

    public Role findById(Long id) {
        return webClient.webClientImpl(url)
            .get().uri(uriBuilder -> uriBuilder.path("/api/{id}").build(id))
            .retrieve()
            .bodyToMono(Role.class)
            .block();
    }

    public Role save(Role role) {
        return webClient.webClientImpl(url)
            .post().uri(uriBuilder -> uriBuilder.path("/api").build())
            .body(Mono.just(role), Role.class)
            .retrieve()
            .bodyToMono(Role.class)
            .block();
    }

    public List<Role> findAllById(List<Long> ids) {
        return webClient.webClientImpl(url)
            .get().uri(uriBuilder -> uriBuilder.path("/api/all-role")
            .queryParam("ids", ids).build())
            .retrieve()
            .bodyToFlux(Role.class)
            .collectList().block();
    }
}

package com.amrsoftware.msvc_user.security.service.login;

import com.amrsoftware.msvc_user.domain.model.role.Role;
import com.amrsoftware.msvc_user.domain.model.user.gateways.UserRepository;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomConnectionFailException;
import com.amrsoftware.msvc_user.infrastructure.webclient.rolewebclient.RoleWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;

import static com.amrsoftware.msvc_user.domain.usecase.util.constant.Constant.MESSAGE_ERROR_CONNECTION;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final RoleWebClient webClient;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userDB = repository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException(String.format("El email %s no existe en el sistema", email))
        );

        List<Role> roles = null;

        try {
            roles = userDB.getRolesUsers().stream().map(role -> webClient.findById(role.getRoleId())).toList();
        } catch (WebClientRequestException ex) {
            throw new CustomConnectionFailException(MESSAGE_ERROR_CONNECTION + ex.getUri());
        }

        var authorities = roles
            .stream().map(role -> new SimpleGrantedAuthority(role.getDescription())).toList();

        return new User(
            email,
            userDB.getPassword(),
            true,
            true,
            true,
            true,
            authorities
        );
    }
}

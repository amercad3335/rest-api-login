package com.amrsoftware.msvc_user.domain.usecase.roleuser;

import com.amrsoftware.msvc_user.domain.model.role.Role;
import com.amrsoftware.msvc_user.domain.model.roleuser.RoleUser;
import com.amrsoftware.msvc_user.domain.model.user.gateways.UserRepository;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomObjectNotFoundException;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomRoleSaveException;
import com.amrsoftware.msvc_user.infrastructure.webclient.rolewebclient.RoleWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.amrsoftware.msvc_user.domain.usecase.util.constant.Constant.MESSAGE_NOT_FOUND_USER;

@RequiredArgsConstructor
public class RoleUserUseCase {
    private final UserRepository repository;
    private final RoleWebClient webClient;

    public Role assignRole(Long userId, Long roleId) {
        var userDB = repository.findById(userId).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );

        try {
            var role = webClient.findById(roleId);
            var roleUser = RoleUser.builder().roleId(role.getId()).build();
            userDB.addRoleUser(roleUser);
            repository.save(userDB);

            return role;
        } catch (WebClientResponseException ex) {
            throw new CustomObjectNotFoundException(ex.getMessage());
        }
    }

    public Role deleteRole(Long userId, Long roleId) {
        var userDB = repository.findById(userId).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );

        try {
            var role = webClient.findById(roleId);
            var roleUser = RoleUser.builder().roleId(role.getId()).build();
            userDB.removeRoleUser(roleUser);
            repository.save(userDB);

            return role;
        } catch (WebClientResponseException ex) {
            throw new CustomObjectNotFoundException(ex.getResponseBodyAsString());
        }
    }

    public Role createRole(Long userId, Role role) {
        var userDB = repository.findById(userId).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );

        try {
            var rol = webClient.save(role);
            var roleUser = RoleUser.builder().roleId(rol.getId()).build();
            userDB.addRoleUser(roleUser);
            repository.save(userDB);

            return rol;
        } catch (WebClientResponseException ex) {
            throw new CustomRoleSaveException(ex.getResponseBodyAsString());
        }
    }
}

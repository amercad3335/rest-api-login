package com.amrsoftware.msvc_role.domain.usecase.role;

import com.amrsoftware.msvc_role.domain.model.role.Role;
import com.amrsoftware.msvc_role.domain.model.role.gateways.RoleRepository;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomConnectionFailException;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomObjectNotFoundException;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomRoleUniqueException;
import com.amrsoftware.msvc_role.infrastructure.webclient.userwebclient.UserWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;

import static com.amrsoftware.msvc_role.domain.usecase.util.constant.Constant.*;

@RequiredArgsConstructor
public class RoleUseCase {
    private final RoleRepository repository;
    private final UserWebClient webClient;

    public List<Role> findAll() {
        return repository.findAll();
    }

    public List<Role> findAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_ROLE)
        );
    }

    public Role save(Role role) {
        if (repository.existsByDescription(role.getDescription())) {
            throw new CustomRoleUniqueException(MESSAGE_ROLE_UNIQUE);
        }
        return repository.save(role);
    }

    public Role update(Role role, Long id) {
        var roleDB = repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_ROLE)
        );

        if (!role.getDescription().equalsIgnoreCase(roleDB.getDescription())
            && repository.existsByDescription(role.getDescription())) {
            throw new CustomRoleUniqueException(MESSAGE_ROLE_UNIQUE);
        }

        roleDB.setDescription(role.getDescription());

        return repository.save(roleDB);
    }

    public void delete(Long id) {
        var roleDB = repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_ROLE)
        );

        try {
            webClient.deleteRoleByRoleId(id);
            roleDB.setStatus(Boolean.FALSE);
            repository.save(roleDB);
        } catch (WebClientRequestException ex) {
            throw new CustomConnectionFailException(MESSAGE_ERROR_CONNECTION + ex.getUri());
        }
    }
}

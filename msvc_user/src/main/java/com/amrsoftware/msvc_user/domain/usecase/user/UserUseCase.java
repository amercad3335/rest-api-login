package com.amrsoftware.msvc_user.domain.usecase.user;

import com.amrsoftware.msvc_user.domain.model.roleuser.RoleUser;
import com.amrsoftware.msvc_user.domain.model.user.User;
import com.amrsoftware.msvc_user.domain.model.user.UserUpdate;
import com.amrsoftware.msvc_user.domain.model.user.gateways.UserRepository;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomConnectionFailException;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomEmailUniqueException;
import com.amrsoftware.msvc_user.domain.usecase.exception.CustomObjectNotFoundException;
import com.amrsoftware.msvc_user.infrastructure.webclient.rolewebclient.RoleWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;

import static com.amrsoftware.msvc_user.domain.usecase.util.constant.Constant.*;

@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository repository;
    private final RoleWebClient webClient;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );
    }

    public User findByIdRoles(Long id) {
        var userDB = repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );

        try {
            List<Long> ids = userDB.getRolesUsers().stream().map(RoleUser::getRoleId).toList();
            var roles = webClient.findAllById(ids);
            userDB.setRoles(roles);
            return userDB;
        } catch (WebClientRequestException ex) {
            throw new CustomConnectionFailException(MESSAGE_ERROR_CONNECTION + ex.getUri());
        }

    }

    public User save(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw  new CustomEmailUniqueException(MESSAGE_EMAIL_UNIQUE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User update(UserUpdate user, Long id) {
        var userDB = repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );
        if (!user.getEmail().equalsIgnoreCase(userDB.getEmail())
            && repository.existsByEmail(user.getEmail())) {
            throw  new CustomEmailUniqueException(MESSAGE_EMAIL_UNIQUE);
        }

        userDB.setFirstname(user.getFirstname());
        userDB.setLastname(user.getLastname());
        userDB.setEmail(user.getEmail());

        return repository.save(userDB);
    }

    public void delete(Long id) {
        var userDB = repository.findById(id).orElseThrow(
            () -> new CustomObjectNotFoundException(MESSAGE_NOT_FOUND_USER)
        );
        repository.deleteRoleByUserId(id);
        userDB.setStatus(Boolean.FALSE);
        repository.save(userDB);
    }

    public void deleteRoleByRoleId(Long id) {
        repository.deleteRoleByRoleId(id);
    }
}

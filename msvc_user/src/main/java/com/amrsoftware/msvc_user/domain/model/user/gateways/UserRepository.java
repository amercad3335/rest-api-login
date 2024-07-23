package com.amrsoftware.msvc_user.domain.model.user.gateways;

import com.amrsoftware.msvc_user.domain.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);;
    User save(User user);

    boolean existsByEmail(String email);
    void deleteRoleByUserId(Long id);

    void deleteRoleByRoleId(Long id);
}

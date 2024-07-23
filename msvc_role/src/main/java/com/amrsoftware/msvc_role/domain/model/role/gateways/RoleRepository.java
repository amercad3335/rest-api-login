package com.amrsoftware.msvc_role.domain.model.role.gateways;

import com.amrsoftware.msvc_role.domain.model.role.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();
    List<Role> findAllById(List<Long> ids);
    Optional<Role> findById(Long id);
    Role save(Role role);
    boolean existsByDescription(String description);
}

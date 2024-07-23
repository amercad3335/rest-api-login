package com.amrsoftware.msvc_role.infrastructure.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDataRepository extends JpaRepository<RoleData, Long> {
    boolean existsByDescription(String description);
}

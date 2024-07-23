package com.amrsoftware.msvc_user.infrastructure.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    boolean existsByEmail(String email);

    @Query(value = "DELETE FROM users_roles WHERE user_id = :id", nativeQuery = true)
    @Modifying
    void deleteRoleByUserId(@Param("id") Long id);

    @Query("DELETE FROM RoleUserData WHERE roleId = :id")
    @Modifying
    void deleteRoleByRoleId(@Param("id") Long id);

    Optional<UserData> findByEmail(String email);
}

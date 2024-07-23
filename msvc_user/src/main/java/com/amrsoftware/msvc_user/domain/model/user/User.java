package com.amrsoftware.msvc_user.domain.model.user;

import com.amrsoftware.msvc_user.domain.model.role.Role;
import com.amrsoftware.msvc_user.domain.model.roleuser.RoleUser;
import com.amrsoftware.msvc_user.validation.email.EmailValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class User {
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @EmailValid
    private String email;
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
    private Set<RoleUser> rolesUsers;
    private List<Role> roles;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean status;

    public User() {
        rolesUsers = new HashSet<>();
        roles = new ArrayList<>();
    }

    public void addRoleUser(RoleUser roleUser) {
        rolesUsers.add(roleUser);
    }

    public void removeRoleUser(RoleUser roleUser) {
        rolesUsers.remove(roleUser);
    }
}

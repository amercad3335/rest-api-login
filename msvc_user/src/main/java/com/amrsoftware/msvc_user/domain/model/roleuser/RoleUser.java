package com.amrsoftware.msvc_user.domain.model.roleuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class RoleUser {
    private Long id;
    private Long roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleUser roleUser = (RoleUser) o;
        return Objects.equals(roleId, roleUser.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}

package com.amrsoftware.msvc_role.domain.model.role;

import com.amrsoftware.msvc_role.validation.role.RoleValid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class Role {
    private Long id;
    @NotBlank
    @RoleValid
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean status;
}

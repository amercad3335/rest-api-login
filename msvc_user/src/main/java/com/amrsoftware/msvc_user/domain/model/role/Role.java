package com.amrsoftware.msvc_user.domain.model.role;

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
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean status;
}

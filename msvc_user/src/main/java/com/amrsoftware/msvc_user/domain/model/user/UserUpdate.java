package com.amrsoftware.msvc_user.domain.model.user;

import com.amrsoftware.msvc_user.validation.email.EmailValid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class UserUpdate {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @EmailValid
    private String email;
}

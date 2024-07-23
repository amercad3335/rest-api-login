package com.amrsoftware.msvc_user.infrastructure.controller.roleuser;

import com.amrsoftware.msvc_user.domain.model.role.Role;
import com.amrsoftware.msvc_user.domain.usecase.roleuser.RoleUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.amrsoftware.msvc_user.infrastructure.controller.util.constant.Constant.API;

@RestController
@RequiredArgsConstructor
public class RoleUserController {
    private final RoleUserUseCase useCase;

    @PostMapping(API + "/create-role/{userId}")
    public ResponseEntity<Role> createRole(@RequestBody Role role, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createRole(userId, role));
    }

    @PutMapping(API + "/assign-role/{userId}/{roleId}")
    public ResponseEntity<Role> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.assignRole(userId, roleId));
    }

    @DeleteMapping(API + "/delete-role/{userId}/{roleId}")
    public ResponseEntity<Role> deleteRole(@PathVariable Long userId, @PathVariable Long roleId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.deleteRole(userId, roleId));
    }
}

package com.amrsoftware.msvc_user.infrastructure.controller.user;

import com.amrsoftware.msvc_user.domain.model.user.User;
import com.amrsoftware.msvc_user.domain.model.user.UserUpdate;
import com.amrsoftware.msvc_user.domain.usecase.user.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amrsoftware.msvc_user.infrastructure.controller.util.constant.Constant.API;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserUseCase useCase;

    @GetMapping(API)
    public List<User> users() {
        return useCase.findAll();
    }

    @GetMapping(API + "/{id}")
    public ResponseEntity<User> userDetails(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.findByIdRoles(id));
    }

    @PostMapping(API)
    public ResponseEntity<User> userSave(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.save(user));
    }

    @PutMapping(API + "/{id}")
    public ResponseEntity<User> userUpdate(@Valid @RequestBody UserUpdate userUpdate, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.update(userUpdate, id));
    }

    @DeleteMapping(API + "/{id}")
    public ResponseEntity<Void> userDelete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(API + "/delete-role-by-id/{id}")
    public ResponseEntity<Void> deleteRoleByRoleId(@PathVariable Long id) {
        useCase.deleteRoleByRoleId(id);
        return ResponseEntity.noContent().build();
    }
}

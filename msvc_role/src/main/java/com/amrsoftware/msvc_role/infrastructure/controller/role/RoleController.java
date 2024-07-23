package com.amrsoftware.msvc_role.infrastructure.controller.role;

import com.amrsoftware.msvc_role.domain.model.role.Role;
import com.amrsoftware.msvc_role.domain.usecase.role.RoleUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amrsoftware.msvc_role.infrastructure.controller.constant.Constant.API;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleUseCase useCase;

    @GetMapping(API)
    public List<Role> roles() {
        return useCase.findAll();
    }

    @GetMapping(API + "/all-role")
    public List<Role> findAllById(@RequestParam List<Long> ids) {
        return useCase.findAllById(ids);
    }

    @GetMapping(API + "/{id}")
    public ResponseEntity<Role> roleDetails(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @PostMapping(API)
    public ResponseEntity<Role> roleSave(@Valid @RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.save(role));
    }

    @PutMapping(API + "/{id}")
    public ResponseEntity<Role> roleUpdate(@Valid @RequestBody Role role, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.update(role, id));
    }

    @DeleteMapping(API + "/{id}")
    public ResponseEntity<Void> roleDelete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}

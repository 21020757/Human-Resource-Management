package org.example.hrm.controller;

import org.example.hrm.dto.RoleDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Role;
import org.example.hrm.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        Set<Role> roles = roleService.getAll();
        return ResponseFactory.success(roles);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseFactory.success(role);
    }
}

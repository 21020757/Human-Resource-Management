package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.Role;

import java.util.Set;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Set<RoleDto> roles;
}

package org.example.hrm.dto;

import lombok.Data;

import java.util.Set;


@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private Set<RoleDto> roles;
    private boolean active;
}

package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.enumeration.RoleName;

@Data
public class RoleDto {
    private Long id;
    private RoleName roleName;
}

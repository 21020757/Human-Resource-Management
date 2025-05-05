package org.example.hrm.service;

import org.example.hrm.dto.RoleDto;
import org.example.hrm.model.Role;
import org.example.hrm.model.enumeration.RoleName;
import java.util.Set;

public interface RoleService {
    Role findByRoleName(RoleName roleName);
    Set<Role> findAllByIds(Set<Long> ids);
    Set<Role> mapToEntity(Set<RoleDto> roles);
}

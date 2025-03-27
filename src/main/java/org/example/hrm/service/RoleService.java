package org.example.hrm.service;

import org.example.hrm.model.Role;
import org.example.hrm.model.enumeration.RoleName;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(RoleName roleName);
}

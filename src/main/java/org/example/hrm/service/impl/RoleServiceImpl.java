package org.example.hrm.service.impl;

import org.example.hrm.model.Role;
import org.example.hrm.model.enumeration.RoleName;
import org.example.hrm.repository.RoleRepository;
import org.example.hrm.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRoleName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}

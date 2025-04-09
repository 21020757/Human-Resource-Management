package org.example.hrm.service.impl;

import org.example.hrm.dto.RoleDto;
import org.example.hrm.model.Role;
import org.example.hrm.model.enumeration.RoleName;
import org.example.hrm.repository.RoleRepository;
import org.example.hrm.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Set<Role> mapToEntity(Set<RoleDto> roles) {
        return roles.stream().map(
                (role) -> roleRepository.findByName(role.getRoleName())
        ).collect(Collectors.toSet());
    }
}

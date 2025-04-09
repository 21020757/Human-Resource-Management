package org.example.hrm.mapper;

import org.example.hrm.dto.RoleDto;
import org.example.hrm.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
    @Override
    @Mapping(source = "name", target = "roleName")
    RoleDto toDto(Role role);

    @Override
    @Mapping(source = "roleName", target = "name")
    Role toEntity(RoleDto dto);

    @Override
    @Mapping(source = "roleName", target = "name")
    List<Role> toEntity(List<RoleDto> dtoList);

    @Override
    @Mapping(source = "name", target = "roleName")
    List<RoleDto> toDto(List<Role> entityList);
}

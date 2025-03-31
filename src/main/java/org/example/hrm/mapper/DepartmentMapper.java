package org.example.hrm.mapper;

import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDto, Department> {
    @Override
    @Mapping(source = "managerId", target = "manager.id")
    Department toEntity(DepartmentDto dto);

    @Override
    @Mapping(source = "manager.id", target = "managerId")
    DepartmentDto toDto(Department entity);

    @Override
    @Mapping(source = "manager.id", target = "managerId")
    List<DepartmentDto> toDto(List<Department> entityList);

    @Override
    @Mapping(source = "managerId", target = "manager.id")
    List<Department> toEntity(List<DepartmentDto> dtoList);
}

package org.example.hrm.mapper;

import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.model.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {
    @Override
    @Mapping(source = "department.departmentName", target = "departmentName")
    EmployeeDto toDto(Employee entity);

    @Override
    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeDto dto);
}

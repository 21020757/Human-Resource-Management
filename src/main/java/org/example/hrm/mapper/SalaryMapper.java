package org.example.hrm.mapper;

import org.example.hrm.dto.SalaryDto;
import org.example.hrm.model.Salary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalaryMapper extends EntityMapper<SalaryDto, Salary> {
    @Override
    @Mapping(source = "employeeId", target = "employee.id")
    Salary toEntity(SalaryDto dto);

    @Override
    @Mapping(source = "employee.id", target = "employeeId")
    SalaryDto toDto(Salary entity);
}

package org.example.hrm.mapper;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper extends EntityMapper<ContractDto, Contract> {
    @Override
    @Mapping(source = "employeeId", target = "employee.id")
    Contract toEntity(ContractDto dto);

    @Override
    @Mapping(source = "employee.id", target = "employeeId")
    ContractDto toDto(Contract entity);
}

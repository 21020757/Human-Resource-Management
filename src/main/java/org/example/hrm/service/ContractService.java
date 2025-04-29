package org.example.hrm.service;

import org.example.hrm.dto.ContractDto;

public interface ContractService {
    ContractDto create(ContractDto dto);
    ContractDto update(ContractDto dto);
    void delete(ContractDto dto);
    ContractDto findByEmployeeId(Long employeeId);
}

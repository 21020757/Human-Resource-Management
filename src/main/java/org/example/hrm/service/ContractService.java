package org.example.hrm.service;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.model.Contract;

public interface ContractService {
    Contract create(ContractDto dto);
    Contract update(ContractDto dto);
    void delete(ContractDto dto);
    Contract findByEmployeeId(Long employeeId);
}

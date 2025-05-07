package org.example.hrm.service;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.dto.response.ContractResponse;
import org.example.hrm.model.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ContractService {
    Contract getByCurrent(Authentication authentication);
    Contract create(ContractDto dto);
    Contract update(ContractDto dto);
    void delete(Long id);
    Contract findByEmployeeId(Long employeeId);
    Page<ContractResponse> search(String keyword, Boolean hasContract, Pageable pageable);
}

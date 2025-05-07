package org.example.hrm.service.impl;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.dto.response.ContractResponse;
import org.example.hrm.model.Contract;
import org.example.hrm.model.Employee;
import org.example.hrm.repository.ContractRepository;
import org.example.hrm.service.ContractService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final EmployeeService employeeService;

    public ContractServiceImpl(ContractRepository contractRepository, EmployeeService employeeService) {
        this.contractRepository = contractRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Contract getByCurrent(Authentication authentication) {
        Employee employee = CommonUtils.getCurrentUser(authentication);
        return contractRepository.findByEmployeeIdAndActiveIsTrue(employee.getId());
    }

    @Override
    public Contract create(ContractDto dto) {
        Contract contract = new Contract();
        if (contractRepository.findByEmployeeIdAndActiveIsTrue(dto.getEmployeeId()) != null) {
            throw new RuntimeException("Nhân viên này đã có hợp đồng");
        }
        CommonUtils.copyPropertiesIgnoreNull(dto, contract);
        Employee employee = employeeService.findByEmployeeId(dto.getEmployeeId());
        contract.setEmployee(employee);
        return contractRepository.save(contract);
    }

    @Override
    public Contract update(ContractDto dto) {
        Long id = dto.getId();
        return contractRepository.findById(id)
                .map(existingContract -> {
                    Contract updatedContract = new Contract();
                    CommonUtils.copyPropertiesIgnoreNull(dto, existingContract);
                    updatedContract.setId(id);
                    return contractRepository.save(updatedContract);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        Contract contract = contractRepository.findById(id).orElse(null);
        assert contract != null;
        contract.setActive(false);
        contractRepository.save(contract);
    }

    @Override
    public Contract findByEmployeeId(Long employeeId) {
        return contractRepository.findByEmployeeIdAndActiveIsTrue(employeeId);
    }

    @Override
    public Page<ContractResponse> search(String keyword, Pageable pageable) {
        Page<Contract> contracts = contractRepository.searchByEmployeeName(keyword, pageable);
        return contracts.map(ContractResponse::new);
    }
}

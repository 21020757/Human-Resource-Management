package org.example.hrm.service.impl;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.mapper.ContractMapper;
import org.example.hrm.mapper.EmployeeMapper;
import org.example.hrm.model.Contract;
import org.example.hrm.repository.ContractRepository;
import org.example.hrm.service.ContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final EmployeeMapper employeeMapper;

    public ContractServiceImpl(ContractRepository contractRepository,
                               ContractMapper contractMapper, EmployeeMapper employeeMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public ContractDto create(ContractDto dto) {
        Contract contract = contractRepository.findById(dto.getId()).orElse(null);
        if (contract == null) {
            contract = new Contract();
        } else {
            BeanUtils.copyProperties(dto, contract);
        }
        contractRepository.save(contract);
        return contractMapper.toDto(contract);
    }

    @Override
    public ContractDto update(ContractDto dto) {
        Long id = dto.getId();
        return contractRepository.findById(id)
                .map(existingContract -> {
                    Contract updatedContract = contractMapper.toEntity(dto);
                    updatedContract.setId(id);
                    Contract savedContract = contractRepository.save(updatedContract);
                    return contractMapper.toDto(savedContract);
                })
                .orElse(null);
    }

    @Override
    public void delete(ContractDto dto) {
        contractRepository.deleteById(dto.getId());
    }

    @Override
    public ContractDto findByEmployeeId(Long employeeId) {
        Contract contract = contractRepository.findByEmployeeIdAndActiveIsTrue(employeeId);
        return contractMapper.toDto(contract);
    }
}

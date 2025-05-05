package org.example.hrm.service.impl;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.model.Contract;
import org.example.hrm.repository.ContractRepository;
import org.example.hrm.service.ContractService;
import org.example.hrm.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public Contract create(ContractDto dto) {
        Contract contract = contractRepository.findById(dto.getId()).orElse(null);
        if (contract == null) {
            contract = new Contract();
        } else {
            BeanUtils.copyProperties(dto, contract);
        }
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
    public void delete(ContractDto dto) {
        contractRepository.deleteById(dto.getId());
    }

    @Override
    public Contract findByEmployeeId(Long employeeId) {
        return contractRepository.findByEmployeeIdAndActiveIsTrue(employeeId);
    }
}

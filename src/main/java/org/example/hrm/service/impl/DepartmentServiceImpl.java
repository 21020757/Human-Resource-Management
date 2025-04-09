package org.example.hrm.service.impl;

import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.mapper.DepartmentMapper;
import org.example.hrm.repository.DepartmentRepository;
import org.example.hrm.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentDto getDepartment(Long id) {
        return departmentMapper.toDto(departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found")));
    }

    @Override
    public DepartmentDto getDepartmentByName(String departmentName) {
        return departmentMapper.toDto(departmentRepository.getByDepartmentName(departmentName));
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        return departmentMapper.toDto(departmentRepository.findAll());
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        return departmentMapper.toDto(departmentRepository.save(departmentMapper.toEntity(departmentDto)));
    }

    @Override
    public void updateDepartment(DepartmentDto departmentDto) {
        departmentRepository.save(departmentMapper.toEntity(departmentDto));
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}

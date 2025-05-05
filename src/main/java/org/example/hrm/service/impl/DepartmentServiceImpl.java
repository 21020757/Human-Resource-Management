package org.example.hrm.service.impl;

import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.model.Department;
import org.example.hrm.repository.DepartmentRepository;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Override
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.getByDepartmentName(departmentName);
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Department createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        CommonUtils.copyPropertiesIgnoreNull(departmentDto, department);
        return departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(DepartmentDto departmentDto) {
        Department department = getDepartment(departmentDto.getId());
        CommonUtils.copyPropertiesIgnoreNull(departmentDto, department);
        departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}

package org.example.hrm.service;

import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.model.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartment(Long id);
    DepartmentDto getDepartmentByName(String departmentName);
    List<DepartmentDto> getAllDepartment();
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    void updateDepartment(DepartmentDto departmentDto);
    void deleteDepartment(Long id);
}

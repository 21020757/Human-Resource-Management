package org.example.hrm.service;

import org.example.hrm.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto getDepartment(Long id);
    DepartmentDto getDepartmentByName(String departmentName);
    List<DepartmentDto> getAllDepartment();
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    void updateDepartment(DepartmentDto departmentDto);
    void deleteDepartment(Long id);
}

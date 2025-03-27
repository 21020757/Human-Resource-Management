package org.example.hrm.dto;

import lombok.Data;

@Data
public class DepartmentDto {
    private Long id;
    private String departmentCode;
    private String departmentName;
    private Long managerId;
}

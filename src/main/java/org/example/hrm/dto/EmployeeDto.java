package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.enumeration.ContractType;
import org.example.hrm.model.enumeration.WorkingStatus;

import java.time.LocalDate;

@Data
public class EmployeeDto {
    private Long id;
    private String employeeCode;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;

    private String position;
    private LocalDate hireDate;
    private ContractType contractType;
    private WorkingStatus workingStatus;
    private String departmentName;
}

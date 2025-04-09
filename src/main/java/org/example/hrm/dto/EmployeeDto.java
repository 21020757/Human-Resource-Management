package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.enumeration.ContractType;

import java.time.LocalDate;

@Data
public class EmployeeDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String idNumber;
    private String position;
    private LocalDate hireDate;
    private ContractType contractType;
    private Long departmentId;
    private boolean active;
}

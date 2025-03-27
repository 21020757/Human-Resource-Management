package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.UserProfile;
import org.example.hrm.model.enumeration.ContractType;
import org.example.hrm.model.enumeration.WorkingStatus;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Employee extends UserProfile {
    private String employeeCode;
    private String position;
    private LocalDate hireDate;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private WorkingStatus workingStatus;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

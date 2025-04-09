package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.UserProfile;
import org.example.hrm.model.enumeration.ContractType;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    private boolean active;
}

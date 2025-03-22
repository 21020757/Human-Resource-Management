package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.ContractType;
import org.example.hrm.model.enumeration.WorkingStatus;

import java.sql.Date;


@Entity
@Getter
@Setter
public class Employee extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String fullName;
    private String employeeCode;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private String position;
    private Date hireDate;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private WorkingStatus workingStatus;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private Department department;
}

package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.util.List;

@Entity
@Getter
@Setter
public class Department extends AbstractAuditingEntity {
    @Id
    private Long id;
    private String departmentName;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private List<Employee> employees;
    @OneToOne
    private Employee manager;
}

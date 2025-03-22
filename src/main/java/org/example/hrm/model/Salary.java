package org.example.hrm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

@Entity
@Getter
@Setter
public class Salary extends AbstractAuditingEntity {
    @Id
    private Long id;
    private int month;
    private int year;
    private double baseSalary;
    private double bonus;
    private double deduction;
    private double netSalary;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

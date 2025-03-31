package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Salary extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int month;
    private int year;
    @Column(precision = 10, scale = 2)
    private BigDecimal baseSalary;
    @Column(precision = 10, scale = 2)
    private BigDecimal bonus;
    @Column(precision = 10, scale = 2)
    private BigDecimal deduction;
    @Column(precision = 10, scale = 2)
    private BigDecimal netSalary;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

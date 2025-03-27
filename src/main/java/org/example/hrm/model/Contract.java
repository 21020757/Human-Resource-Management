package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Contract extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal salary;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

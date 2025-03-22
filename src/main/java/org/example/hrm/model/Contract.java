package org.example.hrm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Contract extends AbstractAuditingEntity {
    @Id
    private Long id;
    private Date startDate;
    private Date endDate;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

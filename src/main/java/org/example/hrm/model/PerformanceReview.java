package org.example.hrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "employee_review")
@Getter
@Setter
public class PerformanceReview extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Column(precision = 10, scale = 2)
    private BigDecimal score;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reviewer_id")
    private Employee reviewer;
    private String comment;
}

package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.AttendanceStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Attendance extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private BigDecimal totalWorkingTime;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}

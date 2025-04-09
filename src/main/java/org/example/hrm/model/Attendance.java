package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.enumeration.AttendanceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalWorkingTime;
    @Column(precision = 10, scale = 2)
    private BigDecimal workDays;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}

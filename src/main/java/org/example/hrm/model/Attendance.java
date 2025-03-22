package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.AttendanceStatus;

import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Attendance extends AbstractAuditingEntity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private Date date;
    private Time checkInTime;
    private Time checkOutTime;
    private long totalWorkingTime;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}

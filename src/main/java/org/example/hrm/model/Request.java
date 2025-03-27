package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.RequestType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Request extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee employee;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private RequestType requestType;
    private LocalDate requestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private boolean approved;
}

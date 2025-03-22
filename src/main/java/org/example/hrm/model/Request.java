package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.RequestType;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Request extends AbstractAuditingEntity {
    @Id
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    private Employee employee;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private RequestType requestType;
    private Timestamp requestedDate;
    private Timestamp startTime;
    private Timestamp endTime;
    private String note;
    private boolean approved;
}

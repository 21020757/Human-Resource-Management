package org.example.hrm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.ReportType;

@Entity
@Getter
@Setter
public class Report extends AbstractAuditingEntity {
    @Id
    private Long id;
    private String title;
    private String content;
    private ReportType reportType;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee reporter;
}

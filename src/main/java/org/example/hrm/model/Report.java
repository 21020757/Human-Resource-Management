package org.example.hrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;
import org.example.hrm.model.enumeration.ReportType;

@Entity
@Getter
@Setter
public class Report extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Employee reporter;
}

package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Job extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String jobDescription;
    private double salary;
    private String position;
    private String requirements;
    private Date postedDate;
    private Date closedDate;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "jobs")
    private Set<Candidate> candidates;
}

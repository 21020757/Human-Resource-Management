package org.example.hrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
    private BigDecimal salary;
    private String position;
    private String requirements;
    private LocalDate postedDate;
    private LocalDate closedDate;
    private String location;
    private int exp;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "department_id")
    private Department department;

    @JsonIgnore
    @ManyToMany(mappedBy = "jobs", fetch = FetchType.LAZY)
    private Set<Candidate> candidates;
}

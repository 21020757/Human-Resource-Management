package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Candidate extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Date dateOfBirth;
    private String IDNumber;
    private String resume;
    private String cvLetter;
    private Date appliedDate;
    @ManyToMany
    @JoinTable(name = "candidate_interview",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_id"))
    private List<Interview> interviews;

    @ManyToMany
    @JoinTable(name = "application",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> jobs;
}

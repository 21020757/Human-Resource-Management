package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.UserProfile;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Candidate extends UserProfile {
    private String resume;
    private String cvLetter;
    private LocalDate appliedDate;
    @ManyToMany
    @JoinTable(name = "candidate_interview",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_id"))
    private Set<Interview> interviews;

    @ManyToMany
    @JoinTable(name = "application",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> jobs;
}

package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.model.abstractModel.AbstractAuditingEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
public class Interview extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date date;
    private Time time;
    private String location;
    private String notes;
    @ManyToMany(mappedBy = "interviews")
    private List<Candidate> candidates;
}

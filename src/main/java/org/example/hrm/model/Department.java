package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentCode;
    private String departmentName;
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}

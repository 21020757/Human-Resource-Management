package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String username;
    private String passwordHash;
    @ManyToMany(targetEntity = Role.class)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;
}

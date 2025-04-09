package org.example.hrm.model.abstractModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public class UserProfile extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String idNumber;
}

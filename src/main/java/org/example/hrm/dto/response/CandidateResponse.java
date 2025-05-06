package org.example.hrm.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CandidateResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String idNumber;

    private String cvLetter;
    private LocalDate appliedDate = LocalDate.now();
    private Set<Long> jobIds;
}

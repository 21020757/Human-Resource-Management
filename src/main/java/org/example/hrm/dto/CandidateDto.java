package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
public class CandidateDto {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String idNumber;
    private MultipartFile resume;
    private String cvLetter;
    private LocalDate appliedDate;
    private Long jobId;
}

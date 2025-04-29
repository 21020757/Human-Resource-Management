package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class CandidateDto {
    private String resume;
    private String cvLetter;
    private LocalDate appliedDate;
    private Set<JobDto> jobs;
}

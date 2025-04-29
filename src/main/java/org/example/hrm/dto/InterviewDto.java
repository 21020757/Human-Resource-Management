package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class InterviewDto {
    private Long id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String notes;
    private Long interviewerId;
    private Set<CandidateDto> candidateDtos;
}

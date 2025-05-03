package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class JobDto {
    private Long id;
    private String jobTitle;
    private String jobDescription;
    private BigDecimal salary;
    private String position;
    private String location;
    private int exp;
    private String requirements;
    private LocalDate postedDate;
    private LocalDate closedDate;
    private Long departmentId;
}

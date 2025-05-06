package org.example.hrm.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class JobResponse {
    private Long id;
    private String jobTitle;
    private String jobDescription;
    private BigDecimal salary;
    private String position;
    private String requirements;
    private LocalDate postedDate;
    private LocalDate closedDate;
    private String location;
    private int exp;
    private Boolean active;
    private Long departmentId;
}

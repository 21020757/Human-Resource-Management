package org.example.hrm.dto;

import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.RequestType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class RequestDto {
    private Long id;
    private Long employeeId;
    private RequestType requestType;
    private LocalDate requestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private boolean approved;
}

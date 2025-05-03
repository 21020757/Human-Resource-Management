package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class RequestDto {
    private Long id;
    private RequestType requestType;
    private LocalDate requestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private String comment;
    private RequestStatus status;
    private boolean approved;
}

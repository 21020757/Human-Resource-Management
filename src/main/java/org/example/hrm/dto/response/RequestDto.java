package org.example.hrm.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestDto {
    private Long id;
    private Long employeeId;
    private RequestType requestType;
    private LocalDate requestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private String comment;
    private RequestStatus status;
    private boolean approved;
    private boolean deleted;
}

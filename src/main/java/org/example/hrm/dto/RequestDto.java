package org.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private String employeeName;
    private RequestType requestType;
    private LocalDate requestedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private String comment;
    private RequestStatus status;
    private Boolean approved;
    private Boolean deleted;

    public RequestDto(Request request) {
        this.id = request.getId();
        this.requestType = request.getRequestType();
        this.requestedDate = request.getRequestedDate();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.note = request.getNote();
        this.comment = request.getComment();
        this.status = request.getStatus();
        this.approved = request.isApproved();
        this.deleted = request.isDeleted();
    }
}

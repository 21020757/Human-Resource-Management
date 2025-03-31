package org.example.hrm.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class AttendanceRequest {
    private LocalTime time;
    private Double latitude;
    private Double longitude;
}

package org.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private LocalDate date;
    private LocalTime checkTime;
    private Double latitude;
    private Double longitude;

    public AttendanceResponse(AttendanceRequest attendanceRequest) {
        this.date = LocalDate.now();
        this.checkTime = attendanceRequest.getTime();
        this.longitude = attendanceRequest.getLongitude();
        this.latitude = attendanceRequest.getLatitude();
    }
}

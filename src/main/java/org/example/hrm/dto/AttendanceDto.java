package org.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hrm.model.enumeration.AttendanceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Long id;
    private Long employeeId;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private BigDecimal totalWorkingTime;
    private BigDecimal workDays;
    private AttendanceStatus status;
}

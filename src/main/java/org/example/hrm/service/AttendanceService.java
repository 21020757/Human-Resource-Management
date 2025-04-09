package org.example.hrm.service;

import org.example.hrm.dto.AttendanceDto;
import org.example.hrm.dto.AttendanceRequest;
import org.example.hrm.dto.AttendanceResponse;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AttendanceService {
    void updateAttendance(AttendanceDto attendanceDto);
    AttendanceResponse recordAttendance(String email, AttendanceRequest request);
    Page<AttendanceDto> findByFilters(Long employeeId, int month, int year, AttendanceStatus status, final Pageable pageable);
}

package org.example.hrm.service;

import org.example.hrm.dto.AttendanceRequest;
import org.example.hrm.dto.CustomResponse;


public interface AttendanceService {
    CustomResponse recordAttendance(String email, AttendanceRequest request);
}

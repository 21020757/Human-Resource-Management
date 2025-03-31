package org.example.hrm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hrm.dto.AttendanceRequest;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<?> timeKeeping(@RequestBody AttendanceRequest attendanceRequest) {
        String email = getCurrentUserEmail();
        CustomResponse customResponse = attendanceService.recordAttendance(email, attendanceRequest);
        return ResponseEntity.ok(customResponse);
    }

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}

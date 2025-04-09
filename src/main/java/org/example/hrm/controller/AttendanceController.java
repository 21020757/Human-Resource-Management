package org.example.hrm.controller;

import org.example.hrm.dto.*;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.example.hrm.service.AttendanceService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;
    public AttendanceController(AttendanceService attendanceService,
                                EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> timeKeeping(Principal principal, @RequestBody AttendanceRequest attendanceRequest) {
        String email = principal.getName();
        AttendanceResponse response = attendanceService.recordAttendance(email, attendanceRequest);
        return ResponseEntity.ok(CustomResponse.builder()
                        .data(response)
                        .message("Attendance record successful!").build());
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUserAttendance(
            Principal principal,
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            @RequestParam(required = false) AttendanceStatus status,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable) {
        String email = principal.getName();
        EmployeeDto employee = employeeService.findByEmail(email);
        Page<AttendanceDto> page = attendanceService.findByFilters(employee.getId(),
                month, year, status, pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .message("Attendance records retrieved for user" + email).build());
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<?> getAllRecordByFilter(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            @RequestParam(required = false) AttendanceStatus status,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AttendanceDto> page = attendanceService.findByFilters(employeeId, month, year, status, pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .message("Attendance records retrieved!").build());
    }

    @PutMapping
    public ResponseEntity<?> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        attendanceService.updateAttendance(attendanceDto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Attendance updated successfully!").build());
    }
}

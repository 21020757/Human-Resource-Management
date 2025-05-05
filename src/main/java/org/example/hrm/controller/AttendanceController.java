package org.example.hrm.controller;

import org.example.hrm.dto.*;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Attendance;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/time-keeping")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;
    public AttendanceController(AttendanceService attendanceService,
                                EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> timeKeeping(Authentication authentication, @RequestBody AttendanceRequest attendanceRequest) {
        String email = authentication.getName();
        AttendanceResponse response = attendanceService.recordAttendance(email, attendanceRequest);
        return ResponseEntity.ok(CustomResponse.builder()
                        .data(response)
                        .message("Attendance record successful!").build());
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUserAttendance(
            Authentication authentication,
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            @RequestParam(required = false) AttendanceStatus status,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable) {
        String email = authentication.getName();
        Employee employee = employeeService.findByEmail(email);
        Page<Attendance> page = attendanceService.findByFilters(
                employee.getId(), month, year, status, pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .message("Attendance records retrieved for user" + email).build());
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<?> getAllRecordsByEmployeeId(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            @RequestParam(required = false) AttendanceStatus status,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Attendance> page = attendanceService.findByFilters(employeeId, month, year, status, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @PutMapping
    public ResponseEntity<?> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        attendanceService.updateAttendance(attendanceDto);
        return ResponseFactory.success("Cập nhật thành công!");
    }
}

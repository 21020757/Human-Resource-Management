package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.ReportDto;
import org.example.hrm.model.Report;
import org.example.hrm.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReportDto dto, Authentication authentication) {
        Report report = reportService.create(dto, authentication);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(report)
                        .build()
        );
    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.ReportDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.exception.CoreErrorCode;
import org.example.hrm.exception.CoreException;
import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.RequestType;
import org.example.hrm.service.ReportService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReportDto dto, Authentication authentication) {
        CommonUtils.validateAuthentication(authentication);
        Report report = reportService.create(dto, authentication);
        return ResponseFactory.success(report);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Report report = reportService.getById(id);
        return ResponseFactory.success(report);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ReportDto dto, Authentication authentication) {
        CommonUtils.validateAuthentication(authentication);
        Report report = reportService.create(dto, authentication);
        return ResponseFactory.success(report);
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RequestType requestType,
            Pageable pageable) {
        Page<Report> page = reportService.search(keyword, requestType, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }
}

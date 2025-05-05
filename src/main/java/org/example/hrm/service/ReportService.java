package org.example.hrm.service;

import org.example.hrm.dto.ReportDto;
import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ReportService {
    Report create(ReportDto dto, Authentication authentication);
    Report getById(Long id);
    Page<Report> search(String keyword, ReportType reportType, Pageable pageable);
    Page<Report> search(String keyword, ReportType reportType, Pageable pageable, Authentication authentication);
    void delete(Long id);
}

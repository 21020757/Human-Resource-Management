package org.example.hrm.service;

import org.example.hrm.dto.ReportDto;
import org.example.hrm.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    Report create(ReportDto dto);
    Report getById(Long id);
    Page<Report> search(String keyword, Long employeeId, Pageable pageable);
    void delete(Long id);
}

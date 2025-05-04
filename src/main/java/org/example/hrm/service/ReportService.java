package org.example.hrm.service;

import org.example.hrm.dto.ReportDto;
import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ReportService {
    Report create(ReportDto dto, Authentication authentication);
    Report getById(Long id);
    Page<Report> search(String keyword, RequestType requestType, Pageable pageable);
    void delete(Long id);
}

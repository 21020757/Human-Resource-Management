package org.example.hrm.service.impl;

import org.example.hrm.dto.ReportDto;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.RequestType;
import org.example.hrm.repository.ReportRepository;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.ReportService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final EmployeeService employeeService;
    public ReportServiceImpl(ReportRepository reportRepository, EmployeeService employeeService) {
        this.reportRepository = reportRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Report create(ReportDto dto, Authentication authentication) {
        Report report = new Report();
        CommonUtils.copyPropertiesIgnoreNull(dto, report);
        Employee employee = employeeService.findByEmail(authentication.getName());
        report.setReporter(employee);
        return reportRepository.save(report);
    }

    @Override
    public Report getById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Report> search(String keyword, RequestType requestType, Pageable pageable) {
        return reportRepository.search(keyword, requestType, pageable);
    }

    @Override
    public void delete(Long id) {

    }
}

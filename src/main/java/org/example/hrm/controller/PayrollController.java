package org.example.hrm.controller;

import org.example.hrm.dto.SalaryDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Salary;
import org.example.hrm.service.PayrollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllSalary(
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            Pageable pageable)
    {
        final Page<SalaryDto> page = payrollService.getSalary(month, year, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getPayrollsById(@PathVariable Long id,
            @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
            Pageable pageable)
    {
        Page<SalaryDto> page = payrollService.getSalaryByEmployee(id, month, year, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getPayrollsById(
                                             @RequestParam(defaultValue = "#{T(java.time.Year).now().value}") int year,
                                             @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().monthValue}") int month,
                                             Authentication authentication,
                                             Pageable pageable)
    {
        Page<SalaryDto> page = payrollService.getSalaryByCurrent(authentication, month, year, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }
}

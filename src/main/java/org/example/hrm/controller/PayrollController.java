package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.SalaryDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Salary;
import org.example.hrm.service.PayrollService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/payroll")
public class PayrollController {
    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllSalary(Pageable pageable)
    {
        final Page<Salary> page = payrollService.getSalary(pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }
}

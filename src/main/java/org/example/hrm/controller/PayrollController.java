package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.Metadata;
import org.example.hrm.dto.SalaryDto;
import org.example.hrm.service.PayrollService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PayrollController {
    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllSalary(Pageable pageable)
    {
        final Page<SalaryDto> page = payrollService.getSalary(pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Employee list retrieved!")
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .build());
    }
}

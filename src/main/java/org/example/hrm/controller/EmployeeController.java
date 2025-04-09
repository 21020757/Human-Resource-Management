package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.Metadata;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        EmployeeDto dto = employeeService.findByEmployeeId(id);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Employee retrieved for id " + id + " successful!")
                .data(dto)
                .build());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto savedEmployee = employeeService.create(dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Employee created successful!")
                .data(savedEmployee)
                .build());
    }
    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto dto) {
        employeeService.update(dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Employee updated successful!")
                .build());
    }
    @GetMapping
    public ResponseEntity<?> searchEmployee(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) boolean active,
            @PageableDefault(sort = {"full_name"},
                    direction = Sort.Direction.ASC) Pageable pageable) {
        final Page<EmployeeDto> page = employeeService.search(keyword,
                position,
                departmentId,
                active,
                pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Search employee for keyword " + keyword + " successful!")
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .build());
    }
}

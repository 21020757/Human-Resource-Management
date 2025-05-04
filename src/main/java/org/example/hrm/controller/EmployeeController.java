package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.Metadata;
import org.example.hrm.model.Employee;
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
        Employee res = employeeService.findByEmployeeId(id);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(res)
                .build());
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<?> getEmployeeByEmail(@RequestParam String email) {
        Employee res = employeeService.findByEmail(email);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(res)
                .build());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto savedEmployee = employeeService.create(dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Tạo nhân viên thành công!")
                .data(savedEmployee)
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto dto) {
        employeeService.update(id, dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Cập nhật nhân viên thành công!")
                .build());
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchEmployee(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(sort = {"fullName"},
                    direction = Sort.Direction.ASC) Pageable pageable) {
        final Page<EmployeeDto> page = employeeService.search(keyword,
                position,
                departmentId,
                active,
                pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Employee deleted successful!")
                .build());
    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Employee;
import org.example.hrm.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@PreAuthorize("(hasRole('ADMIN') or hasRole('MANAGER')) or @customPermissionEvaluator.isHR(authentication)")
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
        return ResponseFactory.success(res);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto dto) {
        Employee savedEmployee = employeeService.create(dto);
        return ResponseFactory.success(savedEmployee, "Tạo nhân viên thành công!");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto dto) {
        employeeService.update(id, dto);
        return ResponseFactory.success("Cập nhật nhân viên thành công!");
    }
    @GetMapping("/search")
    @PreAuthorize("(hasRole('ADMIN') or hasRole('MANAGER')) or @customPermissionEvaluator.isHR(authentication)")
    public ResponseEntity<?> searchEmployee(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String position,
            @RequestParam(name = "departmentId", required = false) Long departmentId,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(sort = {"id"},
                    direction = Sort.Direction.ASC) Pageable pageable) {
        final Page<Employee> page = employeeService.search(keyword,
                position,
                departmentId,
                active,
                pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseFactory.success("Xóa nhân viên thành công!");
    }
}

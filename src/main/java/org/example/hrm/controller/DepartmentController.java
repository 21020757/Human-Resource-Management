package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.hrm.dto.CustomResponse.*;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto dto) {
        DepartmentDto departmentDto = departmentService.createDepartment(dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(departmentDto)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDto dto) {
        departmentService.updateDepartment(dto);
        return ResponseEntity.ok(CustomResponse.builder()
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        List<DepartmentDto> list = departmentService.getAllDepartment();
        return ResponseEntity.ok(CustomResponse.builder()
                .data(list)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
        DepartmentDto departmentDto = departmentService.getDepartment(id);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(departmentDto)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(CustomResponse.builder()
                .build());
    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto dto) {
        CustomResponse customResponse =
                new CustomResponse(true, "Department created!", departmentService.createDepartment(dto));
        return ResponseEntity.ok().body(customResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDto dto) {
        departmentService.updateDepartment(dto);
        CustomResponse customResponse =
                new CustomResponse(true, "Department created!", null);
        return ResponseEntity.ok().body(customResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        CustomResponse customResponse =
                new CustomResponse(true, "Get all department successful!", departmentService.getAllDepartment());
        return ResponseEntity.ok().body(customResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
        CustomResponse customResponse =
                new CustomResponse(true, "", departmentService.getDepartment(id));
        return ResponseEntity.ok().body(customResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        CustomResponse customResponse =
                new CustomResponse(true, "Department deleted!" , null);
        return ResponseEntity.ok().body(customResponse);
    }
}

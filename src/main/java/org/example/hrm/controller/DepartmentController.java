package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Department;
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
        Department department = departmentService.createDepartment(dto);
        return ResponseFactory.success(department);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDto dto) {
        departmentService.updateDepartment(dto);
        return ResponseFactory.success("Cập nhật thành công!");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        List<Department> list = departmentService.getAllDepartment();
        return ResponseFactory.success(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
        Department department = departmentService.getDepartment(id);
        return ResponseFactory.success(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        return ResponseFactory.success("Xóa thành công!");
    }
}

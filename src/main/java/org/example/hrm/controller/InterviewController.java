package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.InterviewDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Interview;
import org.example.hrm.service.InterviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody InterviewDto interviewDto) {
        Interview res = interviewService.create(interviewDto);
        return ResponseFactory.success(res);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody InterviewDto interviewDto) {
        interviewService.update(interviewDto);
        return ResponseFactory.success("Cập nhật thành công!");
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Long interviewerId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Pageable pageable) {
        Page<Interview> page = interviewService.search(interviewerId, startDate, endDate, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Interview interview = interviewService.getById(id);
        return ResponseFactory.success(interview);
    }
}

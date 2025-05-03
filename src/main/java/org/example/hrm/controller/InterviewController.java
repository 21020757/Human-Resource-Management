package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.InterviewDto;
import org.example.hrm.model.Interview;
import org.example.hrm.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(res)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody InterviewDto interviewDto) {
        interviewService.update(interviewDto);
        return ResponseEntity.ok().build();
    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody InterviewDto interviewDto) {
        interviewService.create(interviewDto);
        return ResponseEntity.ok().build();
    }
}

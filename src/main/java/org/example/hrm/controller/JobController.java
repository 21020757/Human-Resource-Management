package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.JobDto;
import org.example.hrm.model.Job;
import org.example.hrm.service.JobService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody JobDto req) {
        Job job = jobService.create(req);
        return ResponseEntity.ok(CustomResponse.builder()
                        .data(job)
                        .message("Tạo job thành công!")
                .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String location,
            Pageable pageable
    ) {
        Page<Job> page = jobService.search(keyword, active, location, pageable);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(page.getContent())
                        .message("Tìm kiếm việc thành công!")
                        .metadata(CommonUtils.buildMetadata(page, pageable))
                        .build()
        );
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(job)
                        .message("Job with id " + id + " found!")
                        .build()
        );
    }

    @PutMapping("/deactivate/{id:\\d+}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        jobService.deactivate(id);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message("Xóa job thành công!")
                        .build()
        );
    }
}

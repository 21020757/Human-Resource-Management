package org.example.hrm.controller;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.response.CandidateResponse;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Interview;
import org.example.hrm.service.CandidateService;
import org.example.hrm.util.CommonUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute CandidateDto candidateDto) {
        Candidate candidate = candidateService.create(candidateDto);
        return ResponseFactory.success(candidate);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean hasInterview,
            Pageable pageable) {
        Page<CandidateResponse> page = candidateService.search(keyword, hasInterview, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CandidateResponse candidate = candidateService.findById(id);
        return ResponseFactory.success(candidate);
    }

    @GetMapping("/{id:\\d+}/interviews")
    public ResponseEntity<?> getInterviews(
            @PathVariable Long id) {
        Set<Interview> interviews = candidateService.getInterviews(id);
        return ResponseFactory.success(interviews);
    }

    @GetMapping("/{id:\\d+}/resume")
    public ResponseEntity<?> downloadResume(@PathVariable Long id) {
        Resource resume = candidateService.downloadResume(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resume_" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resume);
    }
}

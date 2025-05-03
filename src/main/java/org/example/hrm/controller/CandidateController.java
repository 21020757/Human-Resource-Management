package org.example.hrm.controller;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.model.Candidate;
import org.example.hrm.service.CandidateService;
import org.example.hrm.util.CommonUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(CustomResponse.builder()
                .data(candidate)
                .build());
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam(required = false) String keyword, Pageable pageable) {
        Page<Candidate> page = candidateService.search(keyword, pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .build());
    }

    @GetMapping("/{id:\\d+}/resume")
    public ResponseEntity<?> downloadResume(@PathVariable Long id) {
        Resource resume = candidateService.downloadResume(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resume);
    }
}

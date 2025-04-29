package org.example.hrm.service;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {
    CandidateDto create (CandidateDto dto);
    CandidateDto update(CandidateDto dto);
    void delete(Long id);
    Page<CandidateDto> search (String keyword, Pageable pageable);
}

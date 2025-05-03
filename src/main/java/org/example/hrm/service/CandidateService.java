package org.example.hrm.service;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.model.Candidate;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CandidateService {
    Set<Candidate> findAllByIds(Set<Long> ids);
    Candidate create (CandidateDto dto);
    CandidateDto update(CandidateDto dto);
    void delete(Long id);
    Page<Candidate> search (String keyword, Pageable pageable);
    Resource downloadResume(Long id);
}

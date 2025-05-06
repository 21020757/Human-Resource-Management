package org.example.hrm.service;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.dto.response.CandidateResponse;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Interview;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CandidateService {
    CandidateResponse findById(Long id);
    Set<Candidate> findAllByIds(Set<Long> ids);
    Candidate create (CandidateDto dto);
    CandidateResponse update(CandidateDto dto);
    void delete(Long id);
    Page<CandidateResponse> search (String keyword, Boolean hasInterview, Pageable pageable);
    Resource downloadResume(Long id);
    Set<Interview> getInterviews(Long candidateId);
}

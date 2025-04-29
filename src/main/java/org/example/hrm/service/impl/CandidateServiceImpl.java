package org.example.hrm.service.impl;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.mapper.CandidateMapper;
import org.example.hrm.model.Candidate;
import org.example.hrm.repository.CandidateRepository;
import org.example.hrm.service.CandidateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }


    @Override
    public CandidateDto create(CandidateDto dto) {
        Candidate candidate = candidateMapper.toEntity(dto);
        candidateRepository.save(candidate);
        return candidateMapper.toDto(candidate);
    }

    @Override
    public CandidateDto update(CandidateDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    public Page<CandidateDto> search(String keyword, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.search(keyword, pageable);
        return candidates.map(candidateMapper::toDto);
    }
}

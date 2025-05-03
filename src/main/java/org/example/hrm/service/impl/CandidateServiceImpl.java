package org.example.hrm.service.impl;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Job;
import org.example.hrm.repository.CandidateRepository;
import org.example.hrm.service.Base64Service;
import org.example.hrm.service.CandidateService;
import org.example.hrm.service.JobService;
import org.example.hrm.util.CommonUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final JobService jobService;
    private final Base64Service base64Service;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                JobService jobService, Base64Service base64Service) {
        this.candidateRepository = candidateRepository;
        this.jobService = jobService;
        this.base64Service = base64Service;
    }


    @Override
    public Set<Candidate> findAllByIds(Set<Long> ids) {
        return candidateRepository.findAllByIdIn(ids);
    }

    @Override
    public Candidate create(CandidateDto dto) {
        Candidate candidate = candidateRepository.findByEmail(dto.getEmail());
        if (candidate == null) {
            candidate = new Candidate();
            CommonUtils.copyPropertiesIgnoreNull(dto, candidate);
            candidate.setJobs(new HashSet<>());
        }
        Job job = jobService.getJobById(dto.getJobId());
        candidate.getJobs().add(job);
        try {
            String resume = base64Service.encode(dto.getResume());
            candidate.setResume(resume);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return candidateRepository.save(candidate);
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
    public Page<Candidate> search(String keyword, Pageable pageable) {
        return candidateRepository.search(keyword, pageable);
    }

    @Override
    public Resource downloadResume(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow();
        try {
            return base64Service.decode(candidate.getResume());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

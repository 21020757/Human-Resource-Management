package org.example.hrm.service.impl;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.mapper.CandidateMapper;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Job;
import org.example.hrm.repository.CandidateRepository;
import org.example.hrm.service.CandidateService;
import org.example.hrm.service.JobService;
import org.example.hrm.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final JobService jobService;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CandidateMapper candidateMapper,
                                JobService jobService) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.jobService = jobService;
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
}

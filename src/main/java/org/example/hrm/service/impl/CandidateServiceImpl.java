package org.example.hrm.service.impl;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.dto.response.CandidateResponse;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Interview;
import org.example.hrm.model.Job;
import org.example.hrm.repository.CandidateRepository;
import org.example.hrm.repository.JobRepository;
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
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final JobService jobService;
    private final Base64Service base64Service;

    public CandidateServiceImpl(CandidateRepository candidateRepository, JobRepository jobRepository,
                                JobService jobService, Base64Service base64Service) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.base64Service = base64Service;
    }


    @Override
    public CandidateResponse findById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow();
        CandidateResponse response = new CandidateResponse();
        CommonUtils.copyPropertiesIgnoreNull(candidate, response);
        response.setJobIds(
                candidate.getJobs().stream()
                        .map(Job::getId)
                        .collect(Collectors.toSet())
        );

        return response;
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
        Job job = jobRepository.findById(dto.getJobId()).orElseThrow();
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
    public CandidateResponse update(CandidateDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    public Page<CandidateResponse> search(String keyword, Pageable pageable) {
        Page<Candidate> page = candidateRepository.search(keyword, pageable);

        return page.map(candidate -> {
            CandidateResponse response = new CandidateResponse();
            CommonUtils.copyPropertiesIgnoreNull(candidate, response);
            response.setJobIds(
                    candidate.getJobs().stream()
                            .map(Job::getId)
                            .collect(Collectors.toSet())
            );
            return response;
        });
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

    @Override
    public Set<Interview> getInterviews(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();
        return candidate.getInterviews();
    }
}

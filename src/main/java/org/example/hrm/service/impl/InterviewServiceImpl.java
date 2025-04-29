package org.example.hrm.service.impl;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.mapper.InterviewMapper;
import org.example.hrm.model.Interview;
import org.example.hrm.repository.InterviewRepository;
import org.example.hrm.service.InterviewService;
import org.springframework.stereotype.Service;

@Service
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    public InterviewServiceImpl(InterviewRepository interviewRepository,
                                InterviewMapper interviewMapper) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
    }

    @Override
    public void create(InterviewDto interviewDto) {
        Interview interview = interviewMapper.toEntity(interviewDto);
        interviewRepository.save(interview);
    }
}

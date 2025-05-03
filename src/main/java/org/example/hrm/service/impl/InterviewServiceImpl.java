package org.example.hrm.service.impl;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.mapper.InterviewMapper;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Interview;
import org.example.hrm.repository.InterviewRepository;
import org.example.hrm.service.CandidateService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.InterviewService;
import org.example.hrm.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;

    public InterviewServiceImpl(InterviewRepository interviewRepository,
                                InterviewMapper interviewMapper,
                                CandidateService candidateService,
                                EmployeeService employeeService) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
    }

    @Override
    public Interview create(InterviewDto interviewDto) {
        Interview interview;
        if (interviewDto.getId() == null) {
            interview = new Interview();
        } else {
            interview = interviewRepository.findById(interviewDto.getId())
                    .orElseThrow(() -> new NoSuchElementException("Interview not found with id: " + interviewDto.getId()));
        }
        CommonUtils.copyPropertiesIgnoreNull(interviewDto, interview);

        if (interviewDto.getInterviewerId() != null) {
            Employee interviewer = employeeService.findByEmployeeId(interviewDto.getInterviewerId());
            interview.setInterviewer(interviewer);
        }

        if (interviewDto.getCandidateIds() != null && !interviewDto.getCandidateIds().isEmpty()) {
            Set<Candidate> candidates = candidateService.findAllByIds(interviewDto.getCandidateIds());
            interview.setCandidates(candidates);
        }

        return interviewRepository.save(interview);
    }

    @Override
    public void update(InterviewDto interviewDto) {
        interviewRepository.findById(interviewDto.getId()).ifPresent(interview -> CommonUtils.copyPropertiesIgnoreNull(interviewDto, interview));
    }
}

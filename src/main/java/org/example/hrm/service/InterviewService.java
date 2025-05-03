package org.example.hrm.service;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.model.Interview;

public interface InterviewService {
    Interview create(InterviewDto interviewDto);
    void update(InterviewDto interviewDto);
}

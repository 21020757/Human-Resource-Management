package org.example.hrm.service;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.model.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InterviewService {
    Interview create(InterviewDto interviewDto);
    void update(InterviewDto interviewDto);
    Page<Interview> search(Long interviewerId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Interview getById(Long id);
}

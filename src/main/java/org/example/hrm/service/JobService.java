package org.example.hrm.service;

import org.example.hrm.dto.JobDto;
import org.example.hrm.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JobService {
    Job create(JobDto dto);
    Page<Job> search(String keyword, Boolean active, String location, Pageable pageable);
    Job getJobById(Long id);
    void deactivate(Long id);
}

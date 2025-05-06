package org.example.hrm.service;

import org.example.hrm.dto.JobDto;
import org.example.hrm.dto.response.JobResponse;
import org.example.hrm.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JobService {
    Job create(JobDto dto);
    Page<JobResponse> search(String keyword, Boolean active, String location, Pageable pageable);
    JobResponse getJobById(Long id);
    void deactivate(Long id);
}

package org.example.hrm.service.impl;

import org.example.hrm.dto.JobDto;
import org.example.hrm.dto.response.JobResponse;
import org.example.hrm.model.Department;
import org.example.hrm.model.Job;
import org.example.hrm.repository.JobRepository;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.service.JobService;
import org.example.hrm.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final DepartmentService departmentService;

    public JobServiceImpl(JobRepository jobRepository, DepartmentService departmentService) {
        this.jobRepository = jobRepository;
        this.departmentService = departmentService;
    }


    @Override
    public Job create(JobDto dto) {
        Job job = new Job();
        Department department = departmentService.getDepartment(dto.getDepartmentId());

        BeanUtils.copyProperties(dto, job);
        job.setPostedDate(LocalDate.now());
        job.setActive(true);
        job.setDepartment(department);
        return jobRepository.save(job);
    }

    @Override
    public Page<JobResponse> search(String keyword, Boolean active, String location, Pageable pageable) {
        Page<Job> jobs = jobRepository.search(keyword, active, location, pageable);
        return jobs.map(job -> {
            JobResponse jobResponse = new JobResponse();
            CommonUtils.copyPropertiesIgnoreNull(job, jobResponse);
            jobResponse.setDepartmentId(job.getDepartment().getId());
            return jobResponse;
        });
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        JobResponse jobResponse = new JobResponse();
        CommonUtils.copyPropertiesIgnoreNull(job, jobResponse);
        Long departmentId = job.getDepartment().getId();
        jobResponse.setDepartmentId(departmentId);
        return jobResponse;
    }

    @Override
    public void deactivate(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.setActive(false);
        jobRepository.save(job);
    }
}

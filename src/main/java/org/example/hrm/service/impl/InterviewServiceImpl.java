package org.example.hrm.service.impl;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.model.Candidate;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Interview;
import org.example.hrm.model.enumeration.NotificationType;
import org.example.hrm.repository.InterviewRepository;
import org.example.hrm.service.CandidateService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.InterviewService;
import org.example.hrm.service.NotificationService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;
    private final CandidateService candidateService;
    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    public InterviewServiceImpl(InterviewRepository interviewRepository,
                                CandidateService candidateService,
                                EmployeeService employeeService, NotificationService notificationService) {
        this.interviewRepository = interviewRepository;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
        this.notificationService = notificationService;
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
            NotificationRequest notification = new NotificationRequest();
            notification.setMessage(String.format("Bạn được phân công làm người phỏng vấn vào ngày %s ở %s",
                    interviewDto.getDate(), interviewDto.getLocation()));
            notification.setTitle("Thông báo lịch phỏng vấn");
            notification.setReceiverId(interviewer.getId());
            notification.setType(NotificationType.REMINDER);
            notification.setPublic(false);
            notificationService.sendNotification(notification);
        }

        if (interviewDto.getCandidateIds() != null && !interviewDto.getCandidateIds().isEmpty()) {
            Set<Candidate> candidates = candidateService.findAllByIds(interviewDto.getCandidateIds());
            interview.setCandidates(candidates);
            candidates.forEach(c -> c.getInterviews().add(interview));
            interview.setCandidates(candidates);

        }

        return interviewRepository.save(interview);
    }

    @Override
    public void update(InterviewDto interviewDto) {
        interviewRepository.findById(interviewDto.getId()).ifPresent(interview -> CommonUtils.copyPropertiesIgnoreNull(interviewDto, interview));
    }

    @Override
    public Page<Interview> search(Long interviewerId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return interviewRepository.search(interviewerId, startDate, endDate, pageable);
    }


    @Override
    public Interview getById(Long id) {
        return interviewRepository.findById(id).orElseThrow();
    }
}

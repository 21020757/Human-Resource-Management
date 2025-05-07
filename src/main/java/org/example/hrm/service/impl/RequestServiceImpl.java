package org.example.hrm.service.impl;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.exception.CoreException;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.NotificationType;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.example.hrm.repository.RequestRepository;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.NotificationService;
import org.example.hrm.service.RequestService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RequestServiceImpl implements RequestService {
    private final EmployeeService employeeService;
    private final RequestRepository requestRepository;
    private final NotificationService notificationService;

    public RequestServiceImpl(EmployeeService employeeService,
                              RequestRepository requestRepository, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.requestRepository = requestRepository;
        this.notificationService = notificationService;
    }
    @Override
    public Request create(RequestDto dto, Authentication authentication) {
        Employee employee = CommonUtils.getCurrentUser(authentication);
        Request request = requestRepository.findByEmployeeIdAndRequestedDateAndRequestTypeAndDeletedIsFalse(
                employee.getId(),
                dto.getRequestedDate(),
                dto.getRequestType());
        if (request != null) {
            request.setDeleted(true);
            request = new Request();
        } else {
            request = new Request();
        }
        CommonUtils.copyPropertiesIgnoreNull(dto, request);
        request.setEmployee(employee);
        request.setStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    @Override
    public RequestDto getById(Long id) {
        Request request = requestRepository.findById(id).orElseThrow();
        RequestDto requestDto = new RequestDto(request);
        requestDto.setEmployeeName(request.getEmployee().getFullName());

        return requestDto;
    }

    @Override
    public Page<RequestDto> search(Long employeeId, RequestType requestType, RequestStatus requestStatus, Pageable pageable, LocalDate fromDate, LocalDate toDate) {
        Page<Request> requests = requestRepository.search(employeeId, requestType, requestStatus, fromDate, toDate, pageable);

        return requests.map(request -> {
            RequestDto requestDto = new RequestDto(request);
            requestDto.setEmployeeName(request.getEmployee().getFullName());
            return requestDto;
        });
    }

    @Override
    public void confirm(RequestDto dto, Authentication authentication) {
        Request request = requestRepository.findById(dto.getId()).orElseThrow();
        CommonUtils.copyPropertiesIgnoreNull(dto, request);
        NotificationRequest notification = new NotificationRequest();
        notification.setMessage(String.format("Yêu cầu của bạn đã được %s", dto.getStatus().toString().equals("REJECTED") ? "từ chối!": "chấp nhận"));
        notification.setTitle("Yêu cầu của bạn đã được cập nhật!");
        notification.setReceiverId(request.getEmployee().getId());
        notification.setType(NotificationType.REMINDER);
        notification.setPublic(false);
        notificationService.sendNotification(notification);
        requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        Request request = requestRepository.findById(id).orElseThrow();
        request.setDeleted(true);
        requestRepository.save(request);
    }

    @Override
    public Page<RequestDto> getCurrent(Authentication authentication, RequestType requestType, RequestStatus requestStatus, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        Employee employee = CommonUtils.getCurrentUser(authentication);

        Page<Request> requests = requestRepository.search(employee.getId(), requestType, requestStatus, fromDate, toDate, pageable);

        return requests.map(request -> {
            RequestDto requestDto = new RequestDto(request);
            requestDto.setEmployeeName(request.getEmployee().getFullName());
            return requestDto;
        });
    }
}

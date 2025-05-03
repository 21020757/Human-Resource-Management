package org.example.hrm.service.impl;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.exception.CoreException;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.example.hrm.repository.RequestRepository;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.RequestService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    private final EmployeeService employeeService;
    private final RequestRepository requestRepository;

    public RequestServiceImpl(EmployeeService employeeService,
                              RequestRepository requestRepository) {
        this.employeeService = employeeService;
        this.requestRepository = requestRepository;
    }
    @Override
    public Request create(RequestDto dto, Authentication authentication) {
        String email = authentication.getName();
        Employee employee = employeeService.findByEmail(email);
        Request request = new Request();
        CommonUtils.copyPropertiesIgnoreNull(dto, request);
        request.setEmployee(employee);
        request.setStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Request> search(Long employeeId, RequestType requestType, RequestStatus requestStatus, Pageable pageable) {
        return requestRepository.search(employeeId, requestType, requestStatus, pageable);
    }

    @Override
    public void confirm(RequestDto dto) {
        Request request = requestRepository.findById(dto.getId()).orElseThrow();
        CommonUtils.copyPropertiesIgnoreNull(dto, request);
        requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {

    }
}

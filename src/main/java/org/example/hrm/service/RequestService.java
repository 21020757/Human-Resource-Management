package org.example.hrm.service;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface RequestService {
    Request create(RequestDto dto, Authentication authentication);
    RequestDto getById(Long id);
    Page<RequestDto> search(Long employeeId, RequestType requestType, RequestStatus requestStatus, Pageable pageable, LocalDate fromDate, LocalDate toDate);
    void confirm(RequestDto dto, Authentication authentication);
    void delete(Long id);
    Page<RequestDto> getCurrent(Authentication authentication, RequestType requestType, RequestStatus requestStatus, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}

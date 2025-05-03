package org.example.hrm.service;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface RequestService {
    Request create(RequestDto dto, Authentication authentication);
    Request getById(Long id);
    Page<Request> search(Long employeeId, RequestType requestType, RequestStatus requestStatus, Pageable pageable);
    void confirm(RequestDto dto);
    void delete(Long id);
}

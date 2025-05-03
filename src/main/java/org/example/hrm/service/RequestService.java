package org.example.hrm.service;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {
    Request create(RequestDto dto);
    Request getById(Long id);
    Page<Request> search(String keyword, Long employeeId, Pageable pageable);
    void confirm(Long id, Long employeeId, RequestStatus status);
    void delete(Long id);
}

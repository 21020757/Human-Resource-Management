package org.example.hrm.service.impl;

import org.example.hrm.dto.RequestDto;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Override
    public Request create(RequestDto dto) {
        return null;
    }

    @Override
    public Request getById(Long id) {
        return null;
    }

    @Override
    public Page<Request> search(String keyword, Long employeeId, Pageable pageable) {
        return null;
    }

    @Override
    public void confirm(Long id, Long employeeId, RequestStatus status) {

    }

    @Override
    public void delete(Long id) {

    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.RequestDto;
import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.RequestService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService requestService;
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RequestDto dto,
                                    Authentication authentication) {
        return ResponseEntity.ok(CustomResponse.builder()
                .data(requestService.create(dto, authentication))
                .build()
        );
    }

    @PutMapping
    public ResponseEntity<?> confirm(@RequestBody RequestDto dto,
                                    Authentication authentication) {
        requestService.confirm(dto, authentication);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Confirmation")
                .build());
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) RequestType requestType,
            @RequestParam(required = false) RequestStatus requestStatus,
            Pageable pageable) {
        Page<Request> page = requestService.search(employeeId, requestType, requestStatus, pageable);
        return ResponseEntity.ok(CustomResponse.builder()
                .data(page.getContent())
                .metadata(CommonUtils.buildMetadata(page, pageable))
                .build());
    }
}

package org.example.hrm.controller;

import org.example.hrm.service.RequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService requestService;
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }


}

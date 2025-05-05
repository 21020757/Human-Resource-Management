package org.example.hrm.controller;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Contract;
import org.example.hrm.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/contracts")
public class ContractController {
    private final ContractService contractService;
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/get-by-employee/{employee_id:\\d+}")
    public ResponseEntity<?> getByEmployee(@PathVariable long employee_id) {
        Contract contract = contractService.findByEmployeeId(employee_id);
        return ResponseFactory.success(contract);
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody ContractDto contractDto) {
        Contract contract = contractService.create(contractDto);
        return ResponseFactory.success(contract);
    }
}

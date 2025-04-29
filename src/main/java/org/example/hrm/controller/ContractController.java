package org.example.hrm.controller;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.dto.CustomResponse;
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
        ContractDto contractDto = contractService.findByEmployeeId(employee_id);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Xem hợp đồng nhân sự thành công!")
                .data(contractDto)
                .build());
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody ContractDto contractDto) {
        ContractDto res = contractService.create(contractDto);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Tạo hợp đồng nhân sự thành công!")
                .data(res)
                .build());
    }
}

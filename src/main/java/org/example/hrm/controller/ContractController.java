package org.example.hrm.controller;

import org.example.hrm.dto.ContractDto;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.response.ContractResponse;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Contract;
import org.example.hrm.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractService contractService;
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentContracts(Authentication authentication) {
        Contract contract = contractService.getByCurrent(authentication);
        return ResponseFactory.success(contract);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean hasContract,
            Pageable pageable
    ) {
        Page<ContractResponse> page = contractService.search(keyword, hasContract, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/get-by-employee/{id:\\d+}")
    public ResponseEntity<?> getByEmployee(@PathVariable Long id) {
        Contract contract = contractService.findByEmployeeId(id);
        return ResponseFactory.success(contract);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContractDto contractDto) {
        Contract contract = contractService.create(contractDto);
        return ResponseFactory.success(contract);
    }
}

package org.example.hrm.dto.response;

import lombok.Data;
import org.example.hrm.model.Contract;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal salary;
    private Boolean active;
    private String employeeName;

    public ContractResponse(Contract contract) {
        this.id = contract.getId();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.salary = contract.getSalary();
        this.active = contract.getActive();
        this.employeeName = contract.getEmployee().getFullName();
    }
}

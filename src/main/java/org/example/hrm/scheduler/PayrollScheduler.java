package org.example.hrm.scheduler;

import org.example.hrm.service.PayrollService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PayrollScheduler {
    private final PayrollService payrollService;

    public PayrollScheduler(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void initMonthlySalary() {
        try {
            payrollService.initMonthlySalary();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 28-31 * ?")
    public void calculateSalary() {
        try {
            payrollService.calculateSalary();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

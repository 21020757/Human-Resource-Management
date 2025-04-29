package org.example.hrm.service.impl;

import org.example.hrm.constant.Constants;
import org.example.hrm.dto.SalaryDto;
import org.example.hrm.mapper.SalaryMapper;
import org.example.hrm.model.Attendance;
import org.example.hrm.model.Contract;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Salary;
import org.example.hrm.repository.AttendanceRepository;
import org.example.hrm.repository.ContractRepository;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.repository.SalaryRepository;
import org.example.hrm.service.PayrollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final SalaryRepository salaryRepository;
    private final ContractRepository contractRepository;
    private final SalaryMapper salaryMapper;

    public PayrollServiceImpl(EmployeeRepository employeeRepository,
                              AttendanceRepository attendanceRepository,
                              SalaryRepository salaryRepository,
                              ContractRepository contractRepository,
                              SalaryMapper salaryMapper) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.salaryRepository = salaryRepository;
        this.contractRepository = contractRepository;
        this.salaryMapper = salaryMapper;
    }

    @Override
    public void initMonthlySalary() {
        List<Employee> employees = employeeRepository.findAllByActive(true);
        for (Employee employee : employees) {
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            Contract contract = contractRepository.findById(employee.getId()).orElse(null);
            if(contract != null) {
                Salary salary = new Salary();
                salary.setEmployee(employee);
                salary.setMonth(month);
                salary.setYear(year);
                salary.setBaseSalary(contract.getSalary());
                salary.setBonus(BigDecimal.ZERO);
                salary.setDeduction(BigDecimal.ZERO);
                salaryRepository.save(salary);
            }
        }
    }

    @Override
    public void calculateSalary() {
        List<Employee> employees = employeeRepository.findAllByActive(true);
        for (Employee employee : employees) {
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            Salary salary = salaryRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), month, year);
            List<Attendance> attendances = attendanceRepository
                    .findByEmployeeAndDateBetween(employee, startDate, endDate);
            BigDecimal netSalary = calculateNetSalary(attendances,
                    salary.getBaseSalary(),
                    salary.getBonus(),
                    salary.getDeduction());
            salary.setNetSalary(netSalary);
            salaryRepository.save(salary);
        }
    }

    @Override
    public Page<SalaryDto> getSalary(final Pageable pageable) {
        final Page<Salary> page = salaryRepository.findAll(pageable);
        return page.map(salaryMapper::toDto);
    }

    @Override
    public Page<SalaryDto> getSalaryByEmployeeId(Pageable pageable, Long employeeId) {
        return null;
    }

    public BigDecimal calculateNetSalary(List<Attendance> attendances,
                                         BigDecimal baseSalary,
                                         BigDecimal bonus,
                                         BigDecimal deductions) {
        BigDecimal workDays = attendances.stream()
                .map(Attendance::getWorkDays)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return baseSalary.multiply(workDays.divide(Constants.MONTHLY_WORK_DAYS, 2, RoundingMode.HALF_EVEN))
                .add(bonus)
                .subtract(deductions)
                .subtract(baseSalary.multiply(BigDecimal.valueOf(0.01)));
    }
}

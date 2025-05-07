package org.example.hrm.service.impl;

import org.example.hrm.constant.Constants;
import org.example.hrm.dto.SalaryDto;
import org.example.hrm.model.Attendance;
import org.example.hrm.model.Contract;
import org.example.hrm.model.Employee;
import org.example.hrm.model.Salary;
import org.example.hrm.repository.AttendanceRepository;
import org.example.hrm.repository.ContractRepository;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.repository.SalaryRepository;
import org.example.hrm.service.PayrollService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final SalaryRepository salaryRepository;
    private final ContractRepository contractRepository;

    public PayrollServiceImpl(EmployeeRepository employeeRepository,
                              AttendanceRepository attendanceRepository,
                              SalaryRepository salaryRepository,
                              ContractRepository contractRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.salaryRepository = salaryRepository;
        this.contractRepository = contractRepository;
     }

    @Override
    public void initMonthlySalary() {
        List<Employee> employees = employeeRepository.findAllByActive(true);
        for (Employee employee : employees) {
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            Contract contract = contractRepository.findById(employee.getId()).orElse(null);
            if(contract != null) {
                Salary salary = salaryRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), month, year);
                if(salary == null) {
                    salary = new Salary();
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
    }

    @Override
    public Page<SalaryDto> getSalaryByEmployee(Long employeeId, int month, int year, Pageable pageable) {
        Page<Salary> salaries = salaryRepository.findAllByEmployeeIdAndMonthAndYear(employeeId, month, year, pageable);
        return salaries.map(SalaryDto::new);
    }

    @Override
    public Page<SalaryDto> getSalaryByCurrent(Authentication authentication, int month, int year, Pageable pageable) {
        calculateSalary();
        Employee employee = CommonUtils.getCurrentUser(authentication);
        Page<Salary> salaries = salaryRepository.findAllByEmployeeIdAndMonthAndYear(employee.getId(), month, year, pageable);
        return salaries.map(SalaryDto::new);
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
            if(salary == null) {
                continue;
            }
            List<Attendance> attendances = attendanceRepository
                    .findByEmployeeAndDateBetween(employee, startDate, endDate);
            BigDecimal netSalary = calculateNetSalary(attendances,
                    salary.getBaseSalary() != null ? salary.getBaseSalary() : BigDecimal.ZERO,
                    salary.getBonus(),
                    salary.getDeduction());
            salary.setNetSalary(netSalary);
            salaryRepository.save(salary);
        }
    }

    @Override
    public Page<SalaryDto> getSalary(int month, int year, final Pageable pageable) {
        calculateSalary();
        Page<Salary> salaries = salaryRepository.findAllByMonthAndYear(month, year, pageable);
        return salaries.map(SalaryDto::new);
    }

    @Override
    public Page<SalaryDto> getSalaryByEmployeeId(Pageable pageable, Long employeeId) {
        return null;
    }

    public BigDecimal calculateNetSalary(List<Attendance> attendances,
                                         BigDecimal baseSalary,
                                         BigDecimal bonus,
                                         BigDecimal deductions) {
        baseSalary = baseSalary != null ? baseSalary : BigDecimal.ZERO;
        bonus = bonus != null ? bonus : BigDecimal.ZERO;
        deductions = deductions != null ? deductions : BigDecimal.ZERO;

        BigDecimal workDays = attendances.stream()
                .map(a -> a.getWorkDays() != null ? a.getWorkDays() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(workDays + "hehe");
        BigDecimal dailyRate = baseSalary.divide(Constants.MONTHLY_WORK_DAYS, 2, RoundingMode.HALF_EVEN);
        BigDecimal insurance = baseSalary.multiply(BigDecimal.valueOf(0.01));
        System.out.println(dailyRate.multiply(workDays) + "kiki");
        return dailyRate.multiply(workDays)
                .add(bonus)
                .subtract(deductions)
                .subtract(insurance);
    }

}

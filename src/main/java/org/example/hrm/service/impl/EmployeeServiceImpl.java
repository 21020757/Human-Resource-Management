package org.example.hrm.service.impl;

import jakarta.transaction.Transactional;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.exception.EmployeeNotFoundException;
import org.example.hrm.mapper.EmployeeMapper;
import org.example.hrm.model.Department;
import org.example.hrm.model.Employee;
import org.example.hrm.model.event.EmployeeCreatedEvent;
import org.example.hrm.repository.DepartmentRepository;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.service.EmployeeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper,
                               ApplicationEventPublisher eventPublisher,
                               DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.eventPublisher = eventPublisher;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Department department = departmentRepository.getByDepartmentName(employeeDto.getDepartmentName());
        employee.setDepartment(department);
        employee.setHireDate(LocalDate.now());
        eventPublisher.publishEvent(new EmployeeCreatedEvent(employeeDto));
        employeeRepository.save(employee);
        employee.setEmployeeCode(employee.getDepartment().getDepartmentCode() + employee.getId());

        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto findByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()
                        -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.")
        );
        return employeeMapper.toDto(employee);
    }
}

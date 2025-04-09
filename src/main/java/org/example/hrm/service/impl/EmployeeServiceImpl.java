package org.example.hrm.service.impl;

import jakarta.transaction.Transactional;
import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.exception.EmployeeNotFoundException;
import org.example.hrm.mapper.DepartmentMapper;
import org.example.hrm.mapper.EmployeeMapper;
import org.example.hrm.model.Employee;
import org.example.hrm.model.event.EmployeeCreatedEvent;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.service.EmployeeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper,
                               ApplicationEventPublisher eventPublisher,
                               DepartmentMapper departmentMapper,
                               DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.eventPublisher = eventPublisher;
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
    }

    @Transactional
    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        DepartmentDto departmentDto = departmentService.getDepartment(employeeDto.getDepartmentId());
        employee.setDepartment(departmentMapper.toEntity(departmentDto));
        employee.setHireDate(LocalDate.now());
        employee.setActive(true);
        eventPublisher.publishEvent(new EmployeeCreatedEvent(employeeDto));
        employeeRepository.save(employee);
        List<Employee> employees = employeeRepository.getAllEmployeeByDepartmentId(employeeDto.getDepartmentId());
        employee.setEmployeeCode(employee.getDepartment().getDepartmentCode() + employees.size());

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

    @Override
    public EmployeeDto findByEmail(String email) {
        return employeeMapper.toDto(employeeRepository.findEmployeeByEmail(email));
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findEmployeeByEmail(employeeDto.getEmail());
        employeeMapper.partialUpdate(employee, employeeDto);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Page<EmployeeDto> search (String keyword,
                                     String position,
                                     Long departmentId,
                                     boolean active,
                                     Pageable pageable) {
        Page<Employee> page;
        if (keyword == null && position == null && departmentId == null) {
            page = employeeRepository.findAll(pageable);
        } else page = employeeRepository.search(keyword, position, departmentId, active, pageable);
        return page.map(employeeMapper::toDto);
    }
}

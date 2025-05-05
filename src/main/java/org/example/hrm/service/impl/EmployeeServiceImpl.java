package org.example.hrm.service.impl;

import jakarta.transaction.Transactional;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.UserDto;
import org.example.hrm.dto.request.UpdateUserRequest;
import org.example.hrm.exception.EmployeeNotFoundException;
import org.example.hrm.model.Department;
import org.example.hrm.model.Employee;
import org.example.hrm.model.event.EmployeeCreatedEvent;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.UserService;
import org.example.hrm.util.CommonUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final DepartmentService departmentService;
    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ApplicationEventPublisher eventPublisher,
                               DepartmentService departmentService, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.eventPublisher = eventPublisher;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Employee create(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        CommonUtils.copyPropertiesIgnoreNull(employeeDto, employee);
        Department department = departmentService.getDepartment(employeeDto.getDepartmentId());
        employee.setDepartment(department);
        employee.setHireDate(LocalDate.now());
        employee.setActive(true);
        eventPublisher.publishEvent(new EmployeeCreatedEvent(employeeDto));
        int count = employeeRepository.countByDepartmentId(employeeDto.getDepartmentId());
        String code = department.getDepartmentCode() + (count + 1); // đếm trước khi lưu
        employee.setEmployeeCode(code);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee findByEmployeeId(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(()
                        -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.")
                );
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Override
    public void update(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        if(employeeDto.isActive() != employee.isActive()) {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setActive(employeeDto.isActive());
            userService.update(employee.getEmail(), updateUserRequest);
        }
        CommonUtils.copyPropertiesIgnoreNull(employeeDto, employee);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        employee.setActive(false);
        employeeRepository.save(employee);
        userService.deleteByEmail(employee.getEmail());
    }

    @Override
    public Page<Employee> search (String keyword,
                                  String position,
                                  Long departmentId,
                                  Boolean active,
                                  Pageable pageable) {
        Page<Employee> page;
        if (isNoFilter(keyword, position, departmentId, active)) {
            page = employeeRepository.findAll(pageable);
        } else {
            page = employeeRepository.search(keyword, position, departmentId, active, pageable);
        }
        return page;
    }

    private boolean isNoFilter(String keyword, String position, Long departmentId, Boolean active) {
        return (keyword == null || keyword.isBlank()) &&
                (position == null || position.isBlank()) &&
                departmentId == null && active == null;
    }
}

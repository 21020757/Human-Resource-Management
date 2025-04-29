package org.example.hrm.service.impl;

import jakarta.transaction.Transactional;
import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.UserDto;
import org.example.hrm.exception.EmployeeNotFoundException;
import org.example.hrm.mapper.DepartmentMapper;
import org.example.hrm.mapper.EmployeeMapper;
import org.example.hrm.model.Employee;
import org.example.hrm.model.event.EmployeeCreatedEvent;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.UserService;
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
    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;
    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper,
                               ApplicationEventPublisher eventPublisher,
                               DepartmentMapper departmentMapper,
                               DepartmentService departmentService, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.eventPublisher = eventPublisher;
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
        this.userService = userService;
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
    public void update(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        if(employeeDto.isActive() != employee.isActive()) {
            UserDto userDto = new UserDto();
            userDto.setEmail(employee.getEmail());
            userDto.setActive(employeeDto.isActive());
            userService.update(userDto);
        }
        employeeMapper.partialUpdate(employee, employeeDto);
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
    public Page<EmployeeDto> search (String keyword,
                                     String position,
                                     Long departmentId,
                                     Boolean active,
                                     Pageable pageable) {
        Page<Employee> page;
        if (isNoFilter(keyword, position, departmentId)) {
            page = employeeRepository.findAll(pageable);
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(),
                    pageable.getPageSize(), Sort.Direction.ASC, "full_name");
            page = employeeRepository.search(keyword, position, departmentId, active, pageable);
        }
        return page.map(employeeMapper::toDto);
    }

    private boolean isNoFilter(String keyword, String position, Long departmentId) {
        return (keyword == null || keyword.isBlank()) &&
                (position == null || position.isBlank()) &&
                departmentId == null;
    }
}

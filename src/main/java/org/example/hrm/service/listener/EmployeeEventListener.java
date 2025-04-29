package org.example.hrm.service.listener;

import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.dto.UserDto;
import org.example.hrm.model.event.EmployeeCreatedEvent;
import org.example.hrm.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventListener {
    private final UserService userService;
    public EmployeeEventListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void handleEmployeeCreated(EmployeeCreatedEvent event) {
        EmployeeDto employeeDto = event.getEmployeeDto();
        userService.autoCreate(getUserDto(employeeDto));
    }

    public UserDto getUserDto(EmployeeDto employeeDto){
        UserDto userDto = new UserDto();
        userDto.setEmail(employeeDto.getEmail());
        userDto.setFullName(employeeDto.getFullName());
        userDto.setActive(employeeDto.isActive());
        return userDto;
    }
}

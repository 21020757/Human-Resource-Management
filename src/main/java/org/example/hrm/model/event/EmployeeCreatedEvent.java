package org.example.hrm.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.hrm.dto.EmployeeDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCreatedEvent {
    private EmployeeDto employeeDto;
}

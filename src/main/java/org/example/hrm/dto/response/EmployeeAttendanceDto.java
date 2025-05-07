package org.example.hrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hrm.model.Attendance;
import org.example.hrm.model.Employee;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendanceDto {
    private Employee employee;
    private List<Attendance> attendances;
}

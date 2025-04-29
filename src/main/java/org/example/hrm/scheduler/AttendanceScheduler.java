package org.example.hrm.scheduler;

import org.example.hrm.model.Attendance;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.example.hrm.repository.AttendanceRepository;
import org.example.hrm.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceScheduler {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceScheduler(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void initDailyAttendance() {
        LocalDate today = LocalDate.now();
        List<Employee> employees = employeeRepository.findAllByActive(true);
        for (Employee employee : employees) {
            boolean exists = attendanceRepository.existsByEmployeeAndDate(employee, today);
            if(!exists) {
                Attendance attendance = new Attendance();
                attendance.setDate(today);
                attendance.setEmployee(employee);
                attendance.setStatus(AttendanceStatus.ABSENT);
                attendanceRepository.save(attendance);
            }
        }
    }
}

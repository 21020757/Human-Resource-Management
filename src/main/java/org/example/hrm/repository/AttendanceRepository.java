package org.example.hrm.repository;

import org.example.hrm.model.Attendance;
import org.example.hrm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findAttendanceByEmployee_EmailAndDate(String email, LocalDate date);
    Boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
}

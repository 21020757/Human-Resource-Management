package org.example.hrm.repository;

import org.example.hrm.model.Attendance;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    Attendance findAttendanceByEmployee_EmailAndDate(String email, LocalDate date);
    Boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
    Attendance findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    @Query("SELECT a FROM Attendance a WHERE " +
            "(:employeeId IS NULL OR a.employee.id = :employeeId) AND " +
            "a.date BETWEEN :startDate AND :endDate AND " +
            "(:status IS NULL OR a.status = :status)")
    Page<Attendance> findByFilters(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") AttendanceStatus status,
            Pageable pageable);
}

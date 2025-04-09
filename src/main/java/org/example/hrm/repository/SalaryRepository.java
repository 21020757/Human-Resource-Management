package org.example.hrm.repository;

import org.example.hrm.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Salary findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
}

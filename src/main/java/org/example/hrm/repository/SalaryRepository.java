package org.example.hrm.repository;

import org.example.hrm.model.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    @Query("""
    SELECT s FROM Salary s
    JOIN FETCH s.employee e
    WHERE s.month = :month AND s.year = :year
""")
    Page<Salary> findAllByMonthAndYear(@Param("month") int month,
                                       @Param("year") int year,
                                       Pageable pageable);

    Page<Salary> findAllByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year, Pageable pageable);
    Salary findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
}

package org.example.hrm.repository;

import org.example.hrm.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmail(String email);
    List<Employee> getAllEmployeeByDepartmentId(Long departmentId);
    List<Employee> findAllByActive(boolean active);
    @Query(value = """
    SELECT * FROM employee
    WHERE (:keyword IS NULL OR full_name LIKE CONCAT('%', :keyword, '%') OR position LIKE CONCAT('%', :keyword, '%'))                                                                                    
    AND (:departmentId IS NULL OR department_id = :departmentId)
    AND (:position IS NULL OR position = :position)
    AND (:active IS NULL OR active = :active)
    """,
            countQuery = """
    SELECT COUNT(*) FROM employee
    WHERE (:keyword IS NULL OR full_name LIKE CONCAT('%', :keyword, '%') OR position LIKE CONCAT('%', :keyword, '%'))
    AND (:departmentId IS NULL OR department_id = :departmentId)
    AND (:position IS NULL OR position = :position)
    AND (:active IS NULL OR active = :active)
    """,
            nativeQuery = true)
    Page<Employee> search(@Param("keyword") String keyword,
                          @Param("position") String position,
                          @Param("departmentId") Long departmentId,
                          @Param("active") Boolean active,
                          Pageable pageable);

    int countByDepartmentId(Long departmentId);
}

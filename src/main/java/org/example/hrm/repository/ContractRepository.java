package org.example.hrm.repository;

import org.example.hrm.model.Contract;
import org.example.hrm.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findByEmployeeIdAndActiveIsTrue(Long employeeId);

    @Query("""
    SELECT c FROM Contract c
    WHERE (:keyword IS NULL OR LOWER(c.employee.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    AND c.active = true)
""")
    Page<Contract> searchByEmployeeName(@Param("keyword") String keyword, Pageable pageable);

}

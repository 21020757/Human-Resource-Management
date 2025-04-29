package org.example.hrm.repository;

import org.example.hrm.model.Contract;
import org.example.hrm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findByEmployeeIdAndActiveIsTrue(Long employeeId);
}

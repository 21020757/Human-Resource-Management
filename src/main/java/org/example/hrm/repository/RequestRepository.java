package org.example.hrm.repository;

import org.example.hrm.model.Request;
import org.example.hrm.model.enumeration.RequestStatus;
import org.example.hrm.model.enumeration.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(value = "SELECT r FROM Request r WHERE (:employeeId IS NULL OR r.employee.id = :employeeId)" +
            "AND (:requestType IS NULL OR r.requestType = :requestType)" +
            "AND (:requestStatus IS NULL OR r.status = :requestStatus)",
    countQuery = "SELECT r FROM Request r WHERE (:employeeId IS NULL OR r.employee.id = :employeeId)" +
            "AND (:requestType IS NULL OR r.requestType = :requestType)" +
            "AND (:requestStatus IS NULL OR r.status = :requestStatus)",
    nativeQuery = true)
    Page<Request> search(
            @Param("employeeId") Long employeeId,
            @Param("requestType") RequestType requestType,
            @Param("requestStatus") RequestStatus requestStatus,
            Pageable pageable);
}

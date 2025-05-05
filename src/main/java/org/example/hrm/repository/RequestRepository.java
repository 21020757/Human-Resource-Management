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

import java.time.LocalDate;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(value = """
    SELECT * FROM request r 
    WHERE (:employeeId IS NULL OR r.employee_id = :employeeId)
      AND (:requestType IS NULL OR r.request_type = :requestType)
      AND (:requestStatus IS NULL OR r.status = :requestStatus)
      AND ((:fromDate IS NULL OR r.requested_date >= :fromDate)
        AND (:toDate IS NULL OR r.requested_date <= :toDate))
      AND r.deleted = b'0'
    """,
            countQuery = """
    SELECT COUNT(*) FROM request r 
    WHERE (:employeeId IS NULL OR r.employee_id = :employeeId)
      AND (:requestType IS NULL OR r.request_type = :requestType)
      AND (:requestStatus IS NULL OR r.status = :requestStatus)
      AND ((:fromDate IS NULL OR r.requested_date >= :fromDate)
        AND (:toDate IS NULL OR r.requested_date <= :toDate))
      AND r.deleted = b'0'
    """,
            nativeQuery = true)
    Page<Request> search(
            @Param("employeeId") Long employeeId,
            @Param("requestType") RequestType requestType,
            @Param("requestStatus") RequestStatus requestStatus,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable);


    Request findByEmployeeIdAndRequestedDateAndRequestTypeAndDeletedIsFalse(Long employeeId, LocalDate requestedDate, RequestType requestType);
}

package org.example.hrm.repository;

import org.example.hrm.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * FROM job j WHERE ((:keyword IS NULL OR LOWER(j.job_title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR (:keyword IS NULL OR LOWER(j.job_description) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
            "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))" +
            "AND (:active IS NULL OR j.active = :active)",
            countQuery = "SELECT COUNT(*) FROM job j WHERE ((:keyword IS NULL OR LOWER(j.job_title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
                    "OR (:keyword IS NULL OR LOWER(j.job_description) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
                    "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))" +
                    "AND (:active IS NULL OR j.active = :active)",
            nativeQuery = true)
    Page<Job> search(@Param("keyword") String keyword,
                     @Param("active") Boolean active,
                     @Param("location") String location,
                     Pageable pageable);

}

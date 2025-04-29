package org.example.hrm.repository;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
}

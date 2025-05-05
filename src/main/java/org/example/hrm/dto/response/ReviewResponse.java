package org.example.hrm.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.hrm.model.Employee;
import org.example.hrm.model.PerformanceReview;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReviewResponse {
    private Long id;
    private LocalDate date;
    private BigDecimal score;
    private Long employeeId;
    private Long reviewerId;
    private String comment;

    public ReviewResponse(PerformanceReview review) {
        this.id = review.getId();
        this.date = review.getDate();
        this.score = review.getScore();
        this.employeeId = review.getEmployee().getId();
        this.reviewerId = review.getReviewer().getId();
        this.comment = review.getComment();
    }
}

package org.example.hrm.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.ReportType;

@Data
@Builder
public class ReportDto {
    private Long id;
    private String title;
    private String content;
    private ReportType reportType;
    private Long reporterId;
}

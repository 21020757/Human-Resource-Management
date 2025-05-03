package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;
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

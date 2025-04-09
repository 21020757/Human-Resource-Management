package org.example.hrm.dto;

import lombok.*;

@Data
@Builder
public class Metadata {
    private long totalItems;
    private int page;
    private int limit;
    private int totalPages;
}

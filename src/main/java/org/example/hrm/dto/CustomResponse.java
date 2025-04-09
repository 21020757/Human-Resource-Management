package org.example.hrm.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomResponse {
    @Builder.Default
    private boolean success = true;
    private String message;
    private Object data;
    private Metadata metadata;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

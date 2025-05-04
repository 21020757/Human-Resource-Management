package org.example.hrm.dto.response;

import org.example.hrm.dto.CustomResponse;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
    public static ResponseEntity<?> paginationSuccess(Page<?> page, Pageable pageable) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(page.getContent())
                        .metadata(CommonUtils.buildMetadata(page, pageable))
                        .build()
        );
    }

    public static ResponseEntity<?> paginationSuccess(Page<?> page, Pageable pageable, String message) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(page.getContent())
                        .message(message)
                        .metadata(CommonUtils.buildMetadata(page, pageable))
                        .build()
        );
    }

    public static ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<?> success(Object data, String message) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<?> success(String message) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message(message)
                        .build()
        );
    }
}

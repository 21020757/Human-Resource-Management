package org.example.hrm.util;

import org.example.hrm.dto.Metadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public static Metadata buildMetadata(Page<?> page, Pageable pageable) {
        return Metadata.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .build();
    }
}

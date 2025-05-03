package org.example.hrm.util;

import org.example.hrm.dto.Metadata;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

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
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        String[] nullPropertyNames = getNullPropertyNames(source);
        BeanUtils.copyProperties(source, target, nullPropertyNames);
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

}

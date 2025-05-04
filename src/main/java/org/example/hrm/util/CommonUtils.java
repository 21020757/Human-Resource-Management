package org.example.hrm.util;

import org.example.hrm.dto.Metadata;
import org.example.hrm.exception.CoreErrorCode;
import org.example.hrm.exception.CoreException;
import org.example.hrm.model.Employee;
import org.example.hrm.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

@Service
public class CommonUtils {
    private static EmployeeService employeeService;

    public CommonUtils(EmployeeService employeeService) {
        CommonUtils.employeeService = employeeService;
    }


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

    public static void validateAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new CoreException(CoreErrorCode.NOT_LOGIN);
        }
    }

    public static Employee getCurrentUser(Authentication authentication) {
        return employeeService.findByEmail(authentication.getName());
    }
}

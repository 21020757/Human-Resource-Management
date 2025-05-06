package org.example.hrm.config;

import org.example.hrm.model.CustomUserDetails;
import org.example.hrm.model.Department;
import org.example.hrm.model.User;
import org.example.hrm.service.DepartmentService;
import org.example.hrm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator {
    @Autowired
    private EmployeeService employeeService;

    public boolean isHR(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            User user = customUserDetails.getUser();
            String departmentCode = employeeService.findByEmail(user.getEmail()).getDepartment().getDepartmentCode();
            return departmentCode != null && departmentCode.equals("HR");
        }
        return false;
    }
}
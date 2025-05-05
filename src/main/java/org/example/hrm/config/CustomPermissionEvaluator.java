package org.example.hrm.config;

import org.example.hrm.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator {
    public boolean isHR(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            String position = ((CustomUserDetails) authentication.getPrincipal()).getPosition();
            return position != null && position.toLowerCase().contains("hr");
        }
        return false;
    }
}
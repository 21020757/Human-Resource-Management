package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.Role;
import org.example.hrm.model.User;
import java.util.Set;

@Data
public class LoginRes {
    private String fullName;
    private String email;
    private Set<Role> roles;

    public LoginRes(User user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}

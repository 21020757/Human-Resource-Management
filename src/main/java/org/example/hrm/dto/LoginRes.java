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
    private String position;

    public LoginRes(User user, String position) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.position = position;
    }
}

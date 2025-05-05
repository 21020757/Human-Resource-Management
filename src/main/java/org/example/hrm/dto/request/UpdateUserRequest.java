package org.example.hrm.dto.request;

import lombok.Data;

import java.util.Set;


@Data
public class UpdateUserRequest {
    private Long id;
    private String fullName;
    private String email;
    private Set<Long> roleIds;
    private boolean active;
}

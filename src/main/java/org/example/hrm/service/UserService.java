package org.example.hrm.service;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;

public interface UserService {
    UserDto create(SignupRequest signupRequest);
    void autoCreate(UserDto userDto);
    Boolean existsByEmail(String email);
    void changePassword(String email, ChangePasswordRequest request);
    void update(UserDto userDto);
}

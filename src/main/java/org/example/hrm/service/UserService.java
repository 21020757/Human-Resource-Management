package org.example.hrm.service;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(long id);
    List<UserDto> getAll();
    UserDto create(SignupRequest signupRequest);
    void autoCreate(UserDto userDto);
    Boolean existsByEmail(String email);
    void changePassword(String email, ChangePasswordRequest request);
    void update(UserDto userDto);
    void deleteByEmail(String email);
}

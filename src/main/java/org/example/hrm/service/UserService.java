package org.example.hrm.service;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;
import org.example.hrm.dto.request.UpdateUserRequest;
import org.example.hrm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    User getById(long id);
    Page<User> search(String keyword, Pageable pageable);
    User create(SignupRequest signupRequest);
    void autoCreate(UserDto userDto);
    Boolean existsByEmail(String email);
    void changePassword(String email, ChangePasswordRequest request);
    void update(String email, UpdateUserRequest request);
    void deleteByEmail(String email);
}

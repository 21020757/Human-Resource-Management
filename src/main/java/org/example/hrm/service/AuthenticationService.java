package org.example.hrm.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.LoginRes;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;
import org.example.hrm.model.User;

public interface AuthenticationService {
    LoginRes login(LoginRequest loginRequest, HttpServletResponse response);
    void registerAccount(SignupRequest signupRequest);
    void logoutUser(HttpServletResponse response);
}

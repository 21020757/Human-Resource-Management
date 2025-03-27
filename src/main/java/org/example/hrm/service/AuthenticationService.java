package org.example.hrm.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.SignupRequest;

public interface AuthenticationService {
    String login(LoginRequest loginRequest, HttpServletResponse response);
    void registerAccount(SignupRequest signupRequest);
    void logoutUser(HttpServletResponse response);
}

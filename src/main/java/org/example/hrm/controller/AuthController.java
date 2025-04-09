package org.example.hrm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.service.AuthenticationService;
import org.example.hrm.service.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        authenticationService.login(loginRequest, response);
        return ResponseEntity.ok(new CustomResponse(true, "Login successfully!", null));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request){
        authenticationService.registerAccount(signupRequest);
        return ResponseEntity.ok(
                new CustomResponse(true,
                    "Account successfully registered for email: " + signupRequest.getEmail(), null));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        authenticationService.logoutUser(response);
        return ResponseEntity.ok(new CustomResponse(true, "Logout successfully!", null));
    }
}
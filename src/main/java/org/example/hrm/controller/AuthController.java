package org.example.hrm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.LoginRes;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.model.User;
import org.example.hrm.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginRes loginRes = authenticationService.login(loginRequest, response);
        return ResponseEntity.ok(CustomResponse.builder()
                        .data(loginRes)
                .message("Đăng nhập thành công!").build()
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request){
        authenticationService.registerAccount(signupRequest);
        return ResponseEntity.ok(CustomResponse.builder()
                .message("Đăng ký tài khoản thành công cho email: "  + signupRequest.getEmail())
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        authenticationService.logoutUser(response);
        return ResponseEntity.ok(CustomResponse.builder().message("Đăng xuất thành công!").build());
    }
}
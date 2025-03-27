package org.example.hrm.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.exception.CustomAuthenticationException;
import org.example.hrm.util.JwtUtils;
import org.example.hrm.service.AuthenticationService;
import org.example.hrm.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserService userService,
                                     JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.getEmail(), loginRequest.getPassword()
            );
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            String accessToken = jwtUtils.generateAccessToken(loginRequest.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getEmail());

            jwtUtils.setTokenCookies(accessToken, refreshToken, response);
            UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();
            return userDetails.getUsername();
        } catch (Exception e) {
            throw new CustomAuthenticationException("Wrong email or password!");
        }
    }

    @Override
    public void registerAccount(SignupRequest signupRequest) {
        if(userService.existsByEmail(signupRequest.getEmail())){
            throw new EntityExistsException("Email already used");
        }
    }

    @Override
    public void logoutUser(HttpServletResponse response) {
        jwtUtils.removeTokenFromCookie(response);
    }
}

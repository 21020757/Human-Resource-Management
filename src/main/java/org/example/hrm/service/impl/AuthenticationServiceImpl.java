package org.example.hrm.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.dto.LoginRequest;
import org.example.hrm.dto.LoginRes;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;
import org.example.hrm.exception.CustomAuthenticationException;
import org.example.hrm.exception.UserIsDisabledException;
import org.example.hrm.model.CustomUserDetails;
import org.example.hrm.model.Employee;
import org.example.hrm.model.User;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.util.JwtUtils;
import org.example.hrm.service.AuthenticationService;
import org.example.hrm.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserService userService, EmployeeService employeeService,
                                     JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.employeeService = employeeService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginRes login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.getEmail(), loginRequest.getPassword()
            );
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            String accessToken = jwtUtils.generateAccessToken(loginRequest.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getEmail());

            jwtUtils.setTokenCookies(accessToken, refreshToken, response);
            CustomUserDetails userDetails = (CustomUserDetails) authenticationResponse.getPrincipal();
            String position = employeeService.findByEmail(loginRequest.getEmail()).getPosition();
            return new LoginRes(userDetails.getUser(), position);
        } catch (InternalAuthenticationServiceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof UserIsDisabledException) {
                throw (UserIsDisabledException) cause;
            }
            throw new UserIsDisabledException("Email bị vô hiệu hóa!");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Email không tồn tại!");
        } catch (BadCredentialsException e) {
            throw new CustomAuthenticationException("Sai mật khẩu!");
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

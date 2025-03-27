package org.example.hrm.controller;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<CustomResponse> changePassword(@RequestBody ChangePasswordRequest request,
                                                         Principal principal) {
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok(
                new CustomResponse(true, "Password changed!", null));
    }
}

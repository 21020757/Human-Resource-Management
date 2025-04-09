package org.example.hrm.controller;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.UserDto;
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
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,
                                                         Principal principal) {
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok(
                CustomResponse.builder().message("Password changed successful!").build());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok(
                CustomResponse.builder().message("Update successful!").build());
    }
}

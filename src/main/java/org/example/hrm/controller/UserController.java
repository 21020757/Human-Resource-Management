package org.example.hrm.controller;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.UserDto;
import org.example.hrm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,
                                                         Authentication authentication) {
        userService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok(
                CustomResponse.builder().message("Password changed successful!").build());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok(
                CustomResponse.builder().message("Update successful!").build());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(users)
                        .build());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .data(userDto)
                        .build()
        );
    }
}

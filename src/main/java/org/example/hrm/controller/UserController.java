package org.example.hrm.controller;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.dto.UserDto;
import org.example.hrm.dto.request.UpdateUserRequest;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.User;
import org.example.hrm.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return ResponseFactory.success("Đổi mật khẩu thành công!");
    }

    @PutMapping("/update/user-email={email}")
    public ResponseEntity<?> updateUser(
            @PathVariable String email,
            @RequestBody UpdateUserRequest request) {
        userService.update(email, request);
        return ResponseFactory.success("Cập nhật thông tin thành công!");
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAll(
            @RequestParam String keyword,
            Pageable pageable) {
        Page<User> page = userService.search(keyword, pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return ResponseFactory.success(user);
    }
}

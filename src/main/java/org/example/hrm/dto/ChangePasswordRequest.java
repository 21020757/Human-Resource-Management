package org.example.hrm.dto;


public record ChangePasswordRequest(String oldPassword, String newPassword, String confirmPassword) {
}

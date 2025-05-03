package org.example.hrm.exception;

import org.springframework.http.HttpStatus;

public enum CoreErrorCode {
    OUT_OF_RANGE(HttpStatus.BAD_REQUEST,"Checkin/Checkout không thành công! Bạn đang ở ngoài khoảng cách cho phép!"),
    MISS_PARAM(HttpStatus.BAD_REQUEST, "Truyền lên thiếu tham số"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,  "Email không hợp lệ"),

    ;

    private final HttpStatus status;
    private final String message;
    CoreErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }
}

package org.example.hrm.controller;

import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.model.Notification;
import org.example.hrm.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Gửi một thông báo mới
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationService.sendNotification(request);
        return ResponseFactory.success(notification);
    }

    /**
     * Lấy danh sách thông báo của một người dùng (bao gồm public)
     */
    @GetMapping("/current")
    public ResponseEntity<?> getNotificationsForUser(Authentication authentication) {
        List<Notification> notification = notificationService.getNotificationsForUser(authentication);
        return ResponseFactory.success(notification);
    }

    /**
     * Đánh dấu đã đọc thông báo
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseFactory.success();
    }

    /**
     * Xóa một thông báo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseFactory.success();
    }
}

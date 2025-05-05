package org.example.hrm.service;

import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.model.Notification;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(NotificationRequest request);
    List<Notification> getNotificationsForUser(Authentication authentication);
    void markAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
}

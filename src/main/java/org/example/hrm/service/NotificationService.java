package org.example.hrm.service;

import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(NotificationRequest request);
    List<Notification> getNotificationsForUser(Long userId);
    void markAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
}

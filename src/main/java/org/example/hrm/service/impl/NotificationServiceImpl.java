package org.example.hrm.service.impl;

import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.model.Notification;
import org.example.hrm.model.enumeration.NotificationType;
import org.example.hrm.repository.NotificationRepository;
import org.example.hrm.service.NotificationService;
import org.example.hrm.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Gửi thông báo cho một người dùng cụ thể hoặc toàn công ty nếu isPublic = true
     */


    @Override
    public Notification sendNotification(NotificationRequest request) {
        Notification notification = new Notification(request);
        return notificationRepository.save(notification);
    }

    /**
     * Lấy danh sách thông báo của người dùng bao gồm cả thông báo công khai
     */
    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByReceiverIdOrIsPublicTrue(userId);
    }

    /**
     * Đánh dấu một thông báo là đã đọc
     */
    public void markAsRead(Long notificationId) {
        Optional<Notification> optional = notificationRepository.findById(notificationId);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    /**
     * Xóa thông báo
     */
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}

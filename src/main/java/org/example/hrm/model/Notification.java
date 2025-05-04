package org.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.hrm.dto.request.NotificationRequest;
import org.example.hrm.model.enumeration.NotificationType;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private boolean isRead = false;

    private LocalDateTime createdAt;

    // Người nhận
    private Long receiverId;

    private boolean isPublic = true; // true: thông báo toàn công ty

    private NotificationType type; // ENUM ví dụ: SYSTEM, MANUAL, REMINDER

    // Constructors, getters, setters
    public Notification(NotificationRequest req) {
        this.title = req.getTitle();
        this.message = req.getMessage();
        this.createdAt = LocalDateTime.now();
        this.receiverId = req.isPublic() ? null : req.getReceiverId();
        this.isPublic = req.isPublic();
        this.type = req.getType();
    }

    public Notification() {
        this.createdAt = LocalDateTime.now();
    }
}

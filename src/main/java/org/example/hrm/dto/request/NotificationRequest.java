package org.example.hrm.dto.request;

import lombok.Data;
import org.example.hrm.model.enumeration.NotificationType;


@Data
public class NotificationRequest {
    private Long id;
    private Long receiverId; // optional nếu isPublic = true
    private String title;
    private String message;
    private boolean isPublic = true;
    private NotificationType type;
}

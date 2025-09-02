package com.example.demo.entity;

import com.example.demo.serializable.UserNotificationId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_notification")
public class UserNotification {
    @EmbeddedId
    UserNotificationId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    User user;
    @MapsId("notificationId")
    @JoinColumn(name = "notification_id")
    @ManyToOne
    Notification notification;
}

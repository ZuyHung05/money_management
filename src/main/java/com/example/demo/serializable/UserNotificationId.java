package com.example.demo.serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class UserNotificationId implements Serializable {
    Integer notificationId;
    Integer userId;
}

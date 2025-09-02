package com.example.demo.repository;

import com.example.demo.entity.UserNotification;
import com.example.demo.serializable.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {
}

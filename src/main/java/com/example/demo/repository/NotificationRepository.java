package com.example.demo.repository;

import com.example.demo.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query(value = "select * from notification join user_notification on notification.id = notification_id where userid = :userId order by time desc",nativeQuery = true)
    List<Notification> getByUserId(@Param("userId") Integer userId);
    @Query(value = "select count(*) from notification" +
            " join user_notification on notification.id = user_notification.notification_id where is_read = false and userid =:userId",nativeQuery = true)
    Integer getUnreadNotificationByUserId(@Param("userId") Integer userId);
    @Modifying
    @Transactional
    @Query(value = "update notification set is_read = true where id = :notificationId",nativeQuery = true)
    void readNotificationById(@Param("notificationId") Integer notificationId);
    @Modifying
    @Transactional
    @Query(value = "update notification join user_notification on notification.id = user_notification.notification_id set is_read = true where userid =:userId",nativeQuery = true)
    void readAllNotificationByUserId(@Param("userId") Integer userId);
}

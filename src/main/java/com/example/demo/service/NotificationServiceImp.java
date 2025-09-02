package com.example.demo.service;

import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import com.example.demo.entity.UserNotification;
import com.example.demo.model.PnsRequest;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserNotificationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.serializable.UserNotificationId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class NotificationServiceImp implements NotificationService{
    private final FirebaseMessaging firebaseMessaging;
    private final  UserRepository userRepository;
     private final NotificationRepository notificationRepository;

     private final UserNotificationRepository userNotificationRepository;

    public NotificationServiceImp(FirebaseMessaging firebaseMessaging, NotificationRepository notificationRepository, UserRepository userRepository, UserRepository userRepository1, UserNotificationRepository userNotificationRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository1;
        this.userNotificationRepository = userNotificationRepository;
    }

    @Override
    public List<Notification> getNotificationByUserId(Integer id) {
        return notificationRepository.getByUserId(id);
    }

    @Override
    public void createNotification(Map<String,Object> body) {
        Notification notification = new Notification();
        if(body.get("deepLink")!=null) notification.setDeepLink(body.get("deepLink").toString());
        notification.setTitle(body.get("title").toString());
        notification.setNotificationText(body.get("notificationText").toString());
        if(body.get("image")!=null) notification.setImage(body.get("image").toString());
        notification.setName(body.get("name").toString());
        notification.setIsRead(false);
        notification.setTime(LocalDateTime.parse(body.get("time").toString(), DateTimeFormatter.ofPattern("ss:mm:HH dd/MM/yyyy")));
        notificationRepository.save(notification);
        List<User> users = userRepository.findAllById( (List<Integer>) body.get("userIds"));
        if( body.get("userIds")!=null) {
            for (User user : users) {
                if (user.getDeviceToken() != null) {
                    PnsRequest request = new PnsRequest();
                    request.setFcmToken(user.getDeviceToken());
                    pushNotification(request);
                }
                UserNotification userNotification = new UserNotification();
                userNotification.setId(new UserNotificationId());
                userNotification.setUser(user);
                userNotification.setNotification(notification);
                userNotificationRepository.save(userNotification);
            }
        }
        if(body.get("userId")!=null){
            User user = userRepository.getUserById(Integer.parseInt(body.get("userId").toString()));
            UserNotification userNotification = new UserNotification();
            userNotification.setId(new UserNotificationId());
            userNotification.setUser(user);
            userNotification.setNotification(notification);
            userNotificationRepository.save(userNotification);
        }
    }
    public void pushNotification(PnsRequest pnsRequest) {
        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder().build();
        Message message = Message.builder().setToken(pnsRequest.getFcmToken()).setNotification(notification).build();
        try {
            firebaseMessaging.send(message);
            System.out.println("success");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Integer getUnReadNotificationByUserId(Integer integer) {
        return notificationRepository.getUnreadNotificationByUserId(integer);
    }

    @Override
    public void readNotification(Integer notificationId) {
        notificationRepository.readNotificationById(notificationId);
    }

    @Override
    public void readAllNotificationByUserId(Integer userId) {
        notificationRepository.readAllNotificationByUserId(userId);
    }

}

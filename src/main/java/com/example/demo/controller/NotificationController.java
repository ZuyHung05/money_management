package com.example.demo.controller;
import com.example.demo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/create")
    public void createNotification(@RequestBody Map<String,Object> body){
        notificationService.createNotification(body);
    }
    @PutMapping("/readNotification/{id}")
    public void readNotification(@PathVariable("id") Integer id){
        notificationService.readNotification(id);
    }
    @GetMapping("/getNotification/{id}")
    public ResponseEntity<?> getNotificationByUserId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(notificationService.getNotificationByUserId(id));
    }
    @GetMapping("/getUnreadNotification/{id}")
    public Integer getUnreadNotificationByUserId(@PathVariable("id") Integer id){
        return notificationService.getUnReadNotificationByUserId(id);
    }
    @PutMapping("/readAllNotification/{id}")
    public void readAllNotificationByUserId(@PathVariable("id") Integer userId){
        notificationService.readAllNotificationByUserId(userId);
    }
}

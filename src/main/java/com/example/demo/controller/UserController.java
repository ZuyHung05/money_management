package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserDTOByEmail(email));
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<?> getListHouseOfUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getHouseById(id));
    }

    @GetMapping("/house_check/{id}")
    public ResponseEntity<?> isUserHasHouses(@PathVariable int id) {
        return ResponseEntity.ok(userService.existUserHouseByUserId(id));
    }

    @PutMapping("/updateDeviceToken/{id}")
    public void updateDeviceToken(@RequestBody Map<String, Object> token, @PathVariable("id") Integer id) {
        userService.updateUserToken(id, token.get("deviceToken") == null ? null : token.get("deviceToken").toString());
    }

    @GetMapping("/get_user_by_house_and_date/{houseId}")
    public ResponseEntity<?> getUserByHouseIdInRangeOfDate(@PathVariable("houseId") String houseId, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(userService.getUserByHouseIdInRangeOfDate(houseId, date));
    }

    @PutMapping("/leaveHouse/{userId}/{houseId}")
    public void leaveHouse(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        userService.updateLeaveTimeById(userId, houseId, date);
    }

    @PutMapping("/updateInformation/{id}/{username}/{email}")
    public void updateUserInformation(@PathVariable Integer id, @PathVariable String username, @PathVariable String email) {
        userService.updateUserInformationById(id, username, email);
    }

    @GetMapping("/check/email/{email}")
    public Boolean checkByEmail(@PathVariable("email") String email) {
        return userService.existByEmail(email);
    }
}

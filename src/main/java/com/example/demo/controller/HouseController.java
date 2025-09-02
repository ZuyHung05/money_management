package com.example.demo.controller;

import com.example.demo.entity.House;
import com.example.demo.entity.UserHouse;
import com.example.demo.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/houses")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }
    @GetMapping("/{id}/users")
    public ResponseEntity<?> getUsersById(@PathVariable String id){
        return ResponseEntity.ok(houseService.getUsersHouseById(id));
    }
    @GetMapping("/{id}/check")
    public ResponseEntity<?> isHouseExist(@PathVariable String id){
        return ResponseEntity.ok(houseService.existsById(id));
    }
    @GetMapping("/{houseId}/{userId}")
    public ResponseEntity<?> getUsersById(@PathVariable Integer userId,@PathVariable String houseId){
        return ResponseEntity.ok(houseService.existsUserHouseByHouse_IdAndUser_Id(userId, houseId));
    }
    @PutMapping("/join")
    public void joinHouse(@RequestBody UserHouse userHouse){
        houseService.joinHouse(userHouse);
    }
    @PutMapping("/create")
    public void createHouse(@RequestBody House House){
        houseService.saveHouse(House);
    }
    @PutMapping("/update_name/{houseId}")
    public void updateHouseName(@PathVariable("houseId") String houseId,@RequestBody Object name){
        houseService.updateName(houseId, name);
    }
    @GetMapping("/check_old_user/{houseId}/{userId}")
    Boolean existsUserHouseLeaveDateByUser_Id(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId){
        return houseService.existsUserHouseLeaveDateByUser_Id(houseId, userId);
    }
    @PutMapping("/join_old_user/{houseId}/{userId}")
    void updateLeaveTimeToNullById(@PathVariable("userId") Integer userId, @PathVariable("houseId") String houseId){
        houseService.updateLeaveTimeToNullById(userId, houseId);
    }
}

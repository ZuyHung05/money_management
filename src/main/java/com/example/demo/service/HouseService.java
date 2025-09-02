package com.example.demo.service;

import com.example.demo.entity.House;
import com.example.demo.entity.UserHouse;
import com.example.demo.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface HouseService {
     Set<UserDTO> getUsersHouseById(String id);
     Boolean existsById(String id);
     Boolean existsUserHouseByHouse_IdAndUser_Id(Integer userId, String houseId);
     void saveHouse(House house);
     void joinHouse(UserHouse userHouse);
     void updateName(String id, Object name);
     Boolean existsUserHouseLeaveDateByUser_Id( String houseId, Integer userId);
     void updateLeaveTimeToNullById( Integer userId,  String houseId);
}

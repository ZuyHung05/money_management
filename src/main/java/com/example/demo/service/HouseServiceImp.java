package com.example.demo.service;

import com.example.demo.entity.House;
import com.example.demo.entity.User;
import com.example.demo.entity.UserHouse;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.repository.HouseRepository;
import com.example.demo.repository.UserHouseRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class HouseServiceImp implements HouseService{
    private final HouseRepository houseRepository;
    private final UserHouseRepository userHouseRepository;
    public HouseServiceImp(HouseRepository houseRepository, UserHouseRepository userHouseRepository) {
        this.houseRepository = houseRepository;
        this.userHouseRepository = userHouseRepository;
    }


    @Override
    public Set<UserDTO> getUsersHouseById(String id) {
        Set<UserDTO> users = new HashSet<>();
         for(UserHouse userHouse: houseRepository.getHouseById(id).getUserHouses()){
             users.add(UserMapper.toUserDto(userHouse.getUser()));
         }
        return users;
    }

    @Override
    public Boolean existsById(String id) {
        return houseRepository.existsById(id);
    }


    @Override
    public Boolean existsUserHouseByHouse_IdAndUser_Id(Integer userId, String houseId) {
        return userHouseRepository.existsUserHouseByHouse_IdAndUser_Id(houseId,userId);
    }

    @Override
    public void saveHouse(House house) {
        houseRepository.save(house);
    }

    @Override
    public void joinHouse(UserHouse userHouse) {
        User user = new User();
        user.setId(userHouse.getId().getUserId());
        House house = new House();
        house.setId(userHouse.getId().getHouseId());
        userHouse.setUser(user);
        userHouse.setHouse(house);
        userHouseRepository.save(userHouse);
    }

    @Override
    public void updateName(String id, Object name) {
        houseRepository.updateHouseName(id,name.toString());
    }

    @Override
    public Boolean existsUserHouseLeaveDateByUser_Id(String houseId, Integer userId) {
        return userHouseRepository.existsUserHouseLeaveDateByUser_Id(houseId, userId);
    }

    @Override
    public void updateLeaveTimeToNullById(Integer userId, String houseId) {
        userHouseRepository.updateLeaveTimeToNullById(userId, houseId);
    }
}

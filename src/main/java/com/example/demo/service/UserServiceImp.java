package com.example.demo.service;
import com.example.demo.entity.User;
import com.example.demo.entity.UserHouse;
import com.example.demo.model.dto.HouseWithRole;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.mapper.HouseMapper;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.repository.UserGroupRepository;
import com.example.demo.repository.UserHouseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component

public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final UserHouseRepository userHouseRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, UserHouseRepository userHouseRepository, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.userHouseRepository = userHouseRepository;
        this.userGroupRepository = userGroupRepository;
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Set<HouseWithRole> getHouseById(Integer id) {
        Set<HouseWithRole> houseWithRoles = new HashSet<>();
        for(UserHouse userHouse : userHouseRepository.findUserHouseByUser_IdOrderByJoinDateDesc(id)){
            houseWithRoles.add(HouseMapper.toHouseWRole(userHouse));
        }
        return houseWithRoles;
    }


    @Override
    public void updateUserToken(Integer id,String deviceToken) {
        userRepository.updateDeviceTokenById(id,deviceToken);
    }

    @Override
    public void updateLeaveTimeById(Integer userId, String houseId, LocalDate date) {
        userGroupRepository.deleteAllByHouseId_IdAndUser_Id(houseId, userId);
        userHouseRepository.updateLeaveTimeById(userId, houseId, date);
    }

    @Override
    public void updateUserInformationById(Integer userId, String username, String email) {
        userRepository.updateUserInformationById(userId, username, email);
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existUserHouseByUserId(Integer id) {
        return userHouseRepository.existsUserHouseByUser_Id(id);
    }

    @Override
    public UserDTO getUserDTOByEmail(String email) {
        return UserMapper.toUserDto(userRepository.getUserByEmail(email));
    }

    @Override
    public List<Map<String,Object>> getUserByHouseIdInRangeOfDate(String houseId, LocalDate date) {
        return userRepository.getUserByIdAndDate(houseId, date);
    }
}

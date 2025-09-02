package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.model.dto.HouseWithRole;
import com.example.demo.model.dto.UserDTO;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {

     UserDTO getUserDTOByEmail(String email);
     List<Map<String,Object>> getUserByHouseIdInRangeOfDate(String houseId, LocalDate date);
     User getUserByEmail(String email);
     void saveUser(User user);
     boolean isEmailExist(String email);
     Set<HouseWithRole> getHouseById(Integer id);
     boolean existUserHouseByUserId(Integer id);
     void updateUserToken(Integer id, String deviceToken);
     void updateLeaveTimeById(Integer userId, String houseId, LocalDate date);
     void updateUserInformationById(Integer userId,String username,String email);
     Boolean existByEmail(String email);
}

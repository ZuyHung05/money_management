package com.example.demo.model.mapper;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDTO;

public class UserMapper {
    public static UserDTO toUserDto(User user){
        return new UserDTO(user.getId(),user.getUsername(), user.getEmail());
    }
}

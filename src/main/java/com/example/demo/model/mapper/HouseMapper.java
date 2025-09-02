package com.example.demo.model.mapper;
import com.example.demo.entity.UserHouse;
import com.example.demo.model.dto.HouseWithRole;

public class HouseMapper {
    public static HouseWithRole toHouseWRole(UserHouse userHouse){
        return new HouseWithRole(userHouse.getHouse().getId(),userHouse.getHouse().getName(),userHouse.getHouse().getInformation(), userHouse.getHouse().getDate(), userHouse.isUserRole());
    };
}

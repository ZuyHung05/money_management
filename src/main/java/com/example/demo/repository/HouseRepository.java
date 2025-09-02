package com.example.demo.repository;

import com.example.demo.entity.House;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,String> {
     House getHouseById(String id);
    @Modifying
    @Transactional
    @Query(value = "update house  set name = :name where id = :houseId",nativeQuery = true)
    void updateHouseName(String houseId, String name);
}

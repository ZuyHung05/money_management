package com.example.demo.repository;

import com.example.demo.entity.UserHouse;
import com.example.demo.serializable.UserHouseId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface UserHouseRepository extends JpaRepository<UserHouse, UserHouseId> {
    @Query(value = """
                select * from user_house where userid=:id and leave_date is null order by join_date desc
            """, nativeQuery = true)
    Set<UserHouse> findUserHouseByUser_IdOrderByJoinDateDesc(@Param("id") Integer id);

    @Query(value = "select distinct IF(exists(select * from user_house where userid = :userId and house_id = :houseId  ), 'true', 'false') from user_house", nativeQuery = true)
    Boolean existsUserHouseByHouse_IdAndUser_Id(@Param("houseId") String houseId, @Param("userId") Integer userId);

    @Query(value = "select distinct IF(exists(select * from user_house where userid = :id and leave_date is null), 'true', 'false') from user_house", nativeQuery = true)
    Boolean existsUserHouseByUser_Id(Integer id);

    @Query(value = "select distinct IF(exists(select leave_date from user_house where userid = :userId and house_id = :houseId and leave_date is null), 'true', 'false') from user_house", nativeQuery = true)
    Boolean existsUserHouseLeaveDateByUser_Id(@Param("houseId") String houseId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "update user_house  set leave_date = :date where userid = :userId and house_id = :houseId", nativeQuery = true)
    void updateLeaveTimeById(@Param("userId") Integer userId, @Param("houseId") String houseId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "update user_house  set leave_date = null where userid = :userId and house_id = :houseId", nativeQuery = true)
    void updateLeaveTimeToNullById(@Param("userId") Integer userId, @Param("houseId") String houseId);
}

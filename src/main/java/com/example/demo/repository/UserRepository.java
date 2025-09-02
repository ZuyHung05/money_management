package com.example.demo.repository;

import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = """
            select username,email,id from user
            join (
                 select*from user_house as uh1 where uh1.house_id = :houseId
             except
                      (select*from user_house as uh2
                             where (uh2.join_date > :date or uh2.leave_date <= :date) and uh2.house_id = :houseId)) as uh
                    on uh.userid = user.id
                    """, nativeQuery = true)
    List<Map<String, Object>> getUserByIdAndDate(@Param("houseId") String houseId, @Param("date") LocalDate date);



    @Modifying
    @Transactional
    @Query(value = "update user  set device_token = :deviceToken where id = :userId", nativeQuery = true)
    void updateDeviceTokenById(@Param("userId") Integer userId, @Param("deviceToken") String deviceToken);
    @Modifying
    @Transactional
    @Query(value = "update user  set username = :username,email=:email where id = :userId", nativeQuery = true)
    void updateUserInformationById(@Param("userId") Integer userId, @Param("username") String username,@Param("email") String email);

}

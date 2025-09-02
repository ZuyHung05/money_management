package com.example.demo.repository;

import com.example.demo.entity.PaymentGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentGroupRepository extends JpaRepository<PaymentGroup, Integer> {
    PaymentGroup getPaymentGroupById(Integer id);

    @Query(value = "SELECT * FROM payment_group  " +
            "WHERE id = :id and is_removed = true", nativeQuery = true)
    PaymentGroup getRemovedPaymentGroupById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM payment_group  " +

            "WHERE house_id = :id and is_removed = false", nativeQuery = true)
    List<PaymentGroup> getPaymentGroupsByHouse_Id(@Param("id") String id);
    @Query(value = "select IF(exists(select id from payment_group where house_id = :houseId and is_removed = false and name = :name),'true','false')",nativeQuery = true)
    Boolean checkGroup(@Param("houseId") String houseId,@Param("name") String name);
    @Modifying
    @Transactional
    @Query(value = "update payment_group  " +

            "set is_removed = true where id = :id ", nativeQuery = true)
    void removeGroupById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM payment_group pg " +
            "JOIN user_group ug ON pg.id = ug.group_id " +
            "WHERE ug.userid = :userId AND pg.house_id = :houseId and is_removed = false", nativeQuery = true)
    List<PaymentGroup> getPaymentGroupByUserAndHouse(@Param("userId") Integer userId, @Param("houseId") String houseId);
}

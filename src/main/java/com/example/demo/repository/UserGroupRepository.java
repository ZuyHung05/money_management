package com.example.demo.repository;

import com.example.demo.entity.UserGroup;
import com.example.demo.serializable.UserGroupId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,UserGroupId> {
    @Modifying
    @Transactional
    @Query(value = """
                delete from user_group where group_id =  :id
            """,nativeQuery = true)
    void deleteAllByPaymentGroup_Id(@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value = """
    delete from user_group where group_id in (select group_id from payment_group where house_id = :houseId) and userid =:userId
""",nativeQuery = true)
    void deleteAllByHouseId_IdAndUser_Id(@Param("houseId") String houseId,@Param("userId") Integer userId);

}

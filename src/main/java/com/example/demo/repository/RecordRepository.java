package com.example.demo.repository;

import com.example.demo.entity.Record;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecordRepository extends JpaRepository<Record, String> {
    @Query(value = "SELECT * FROM record r " +
            "JOIN user_record ur ON r.id = ur.record_id " +
            "WHERE  ur.participant_id = :userId AND r.house_id = :houseId AND year(r.date) = :year AND month(r.date) = :month AND is_removed = false ORDER BY r.date desc ", nativeQuery = true)
    List<Record> findRecordsByParticipants(@Param("userId") Integer userId, @Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    @Query(value = "SELECT * FROM record r " +
            "JOIN user_record ur ON r.id = ur.record_id " +
            "WHERE ur.participant_id = :userId AND r.payment_group = :groupId AND r.house_id = :houseId AND year(r.date) = :year AND month(r.date) = :month  AND is_removed = false ORDER BY r.date desc", nativeQuery = true)
    List<Record> findRecordsByParticipantsAndPaymentGroup(@Param("userId") Integer userId, @Param("houseId") String houseId, @Param("groupId") Integer groupId, @Param("year") String year, @Param("month") String month);

    @Query(value = "SELECT * FROM record r " +
            "WHERE r.payer_id = :payerId AND r.payment_group = :groupId AND r.house_id = :houseId AND year(r.date) = :year AND month(r.date) = :month  AND is_removed = false ORDER BY r.date desc", nativeQuery = true)
    List<Record> findRecordsByPayer_IdAndPaymentGroupAndHouse_Id(@Param("payerId") Integer payerId, @Param("groupId") Integer groupId, @Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    @Query(value = "SELECT * FROM record r " +
            "WHERE r.payer_id = :payerId AND r.house_id = :houseId AND year(r.date) = :year AND month(r.date) = :month  AND is_removed = false ORDER BY r.date desc", nativeQuery = true)
    List<Record> findRecordsByPayer_IdAndHouse_Id(@Param("payerId") Integer payerId, @Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    @Query(value = "SELECT * FROM record r " +
            "WHERE r.payment_group = 0  AND r.house_id = :houseId AND year(r.date) = :year AND month(r.date) = :month  AND is_removed = false ORDER BY r.date desc", nativeQuery = true)
    List<Record> findRecordsByHouseForAllMember(@Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    @Query(value = "select distinct record_date from (SELECT distinct CONCAT(LPAD(month(date), 2, '0'), '/', year(date)) AS record_date,date from record where house_id = :houseId  AND is_removed = false  ORDER BY date desc) as rdd", nativeQuery = true)
    List<Object> findDateOfRecord(@Param("houseId") String houseId);

    @Query(value = """
            SELECT sum(record.money/(total.participants)) from  record
            join (select usr1.record_id, count(usr1.participant_id) as  participants from user_record usr1  group by record_id) as total on total.record_id = record.id
            join (select distinct usr2.record_id from user_record usr2 where  usr2.participant_id = :userId) as usr on  total.record_id = usr.record_id where year(date) = :year AND month(date) = :month AND house_id = :houseId  AND is_removed = false""", nativeQuery = true)
    Integer findDebtMoneyByDate(@Param("userId") Integer userId, @Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    @Query(value = "SELECT sum(record.money) from  record where payer_id = :userId AND year(date) = :year AND month(date) = :month AND house_id = :houseId  AND is_removed = false", nativeQuery = true)
    Integer findPaidMoneyByDate(@Param("userId") Integer userId, @Param("houseId") String houseId, @Param("year") String year, @Param("month") String month);

    Record getRecordById(String id);
    @Query(value = """
                select * from record where is_removed = true and id=:id
            """,nativeQuery = true)
    Record getRemovedRecordById(@Param("id") String id);
    @Modifying
    @Transactional
    @Query(value = """
               update record set is_removed = true where id =:id
            """,nativeQuery = true)
    void removeRecordById(@Param("id") String id);


    @Query(value = " SELECT sum(record.money / (total.participants)) as totalMoney, DATE_FORMAT(record.date, '%Y/%m') as byMonth " +
            " from record" +
            "         join (select usr1.record_id, count(usr1.participant_id) as participants" +
            "               from user_record usr1" +
            "               group by record_id) as total on total.record_id = record.id" +
            "         join (select distinct usr2.record_id from user_record usr2 where usr2.participant_id = :userId) as usr" +
            "              on total.record_id = usr.record_id" +
            " where house_id =:houseId  and is_removed = false " +
            " group by byMonth " +
            " order by byMonth", nativeQuery = true)
    List<Map<String, Object>> getTotalFeeByMonth(@Param("houseId") String houseId, @Param("userId") Integer userId);

    @Query(value = """
        
            select sum(record.money) as totalMoney, DATE_FORMAT(record.date, '%Y/%m') as byMonth
            from record
            where house_id = :houseId
            and payer_id = :userId and is_removed = false
            group by byMonth
            order by byMonth
            
            """, nativeQuery = true)
    List<Map<String, Object>> getTotalPaidByMonth(@Param("houseId") String houseId, @Param("userId") Integer userId);

    @Query(value = """ 
            select sum(record.money) as totalMoney, payment_group paymentGroup
                                  from record
                                  where  house_id = :houseId and is_removed = false
                                    and DATE_FORMAT(record.date, '%Y-%m') = :date and payer_id = :userId
                                  group by record.payment_group order by record.payment_group desc
            """, nativeQuery = true)
        List<Map<String, Object>> getPaidForByMonth(@Param("houseId") String houseId, @Param("userId") Integer userId, @Param("date") String date);

    @Query(value = """
            SELECT sum(record.money / (total.participants)) as totalMoney, payment_group paymentGroup
            from record
                     join (select usr1.record_id, count(usr1.participant_id) as participants
                           from user_record usr1
                           group by record_id) as total on total.record_id = record.id
                     join (select distinct usr2.record_id from user_record usr2 where usr2.participant_id = :userId) as usr
                          on total.record_id = usr.record_id
            where house_id = :houseId and DATE_FORMAT(record.date, '%Y-%m') = :date and is_removed = false
            group by record.payment_group
            order by record.payment_group desc
            """, nativeQuery = true)
   List<Map<String, Object>> getFeeFromByMonth(@Param("houseId") String houseId, @Param("userId") Integer userId, @Param("date") String date);

    @Query(value = """
            SELECT sum(record.money / (total.participants)) as totalFee,
                   u.id, u.username
            from record

                     join (select usr1.record_id, count(usr1.participant_id) as participants
                           from user_record usr1
                           group by record_id) as total on total.record_id = record.id

                     join (select distinct usr2.record_id, usr2.participant_id from user_record usr2) as usr
                          on total.record_id = usr.record_id
                     join user u on u.id = usr.participant_id
            where house_id = :houseId
              and DATE_FORMAT(record.date, '%Y-%m') = :date and is_removed = false
            group by u.id, u.email, u.username order by u.id
            """ ,nativeQuery = true)
    List<Map<String, Object>> getSummaryFee(@Param("houseId") String houseId, @Param("date") String date);

    @Query(value = """
            select sum(record.money) as paid, user.id
            from record
                     join user on user.id = record.payer_id
            where house_id = :houseId
            and DATE_FORMAT(record.date, '%Y-%m') =:date and is_removed = false
            group by  user.id, username, email order by user.id
            """,nativeQuery = true)
    List<Map<String, Object>> getSummaryPaid(@Param("houseId") String houseId, @Param("date") String date);
}

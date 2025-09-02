package com.example.demo.service;

import com.example.demo.model.dto.RecordBody;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
public interface RecordService {
    List<RecordBody> getRecordByUsersAndGroup(Integer userId, Integer groupId, String HouseId, String year, String month);

    List<RecordBody> getAllRecordByUsersAndHouse(Integer userId, String houseId, String year, String month);

    List<RecordBody> getRecordByHouseForAllMember(String houseId, String year, String month);

    List<RecordBody> getAllRecordByPayerAndHouse(Integer payerId, String houseId, String year, String month);

    List<RecordBody> getRecordByPayerAndGroup(Integer payerId, Integer groupId, String houseId, String year, String month);

    List<RecordBody> getRecordByPayerAndHouse(Integer payerId, String houseId, String year, String month);

    List<RecordBody> getRecordByUsersAndOther(Integer userId, String houseId, String year, String month);

    List<RecordBody> getRecordByPayerAndOther(Integer payerId, String houseId, String year, String month);

    void saveRecord(RecordBody recordBody, String id);

    List<Object> findDateOfRecords(String houseId);

    Integer findPaidMoneyByDate(Integer userId, String houseId, String year, String month);

    Integer findDebtMoneyByDate(Integer userId, String houseId, String year, String month);

    RecordBody getRecordById(String id);
    RecordBody getRemovedRecordById(String id);
    void removeRecordById(String id);
    //
    List<Map<String, Object>> getTotalFeeByMonth(String houseId, Integer userId);

    List<Map<String, Object>> getTotalPaidByMonth(String houseId, Integer userId);

    List<Map<String, Object>> getPaidForByMonth(String houseId, Integer userId, YearMonth date);

    List<Map<String, Object>> getFeeFromByMonth(String houseId, Integer userId, YearMonth date);
    List<Map<String,Object>> getSummaryByMonth(String houseId,YearMonth date);
}

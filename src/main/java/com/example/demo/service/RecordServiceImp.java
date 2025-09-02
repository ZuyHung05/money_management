package com.example.demo.service;

import com.example.demo.Algorithm;
import com.example.demo.entity.House;
import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRecord;
import com.example.demo.model.dto.RecordBody;
import com.example.demo.model.mapper.RecordMapper;
import com.example.demo.repository.PaymentGroupRepository;
import com.example.demo.repository.RecordRepository;
import com.example.demo.repository.UserRecordRepository;
import com.example.demo.serializable.UserRecordId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.*;

@Component
public class RecordServiceImp implements RecordService {
    private final RecordRepository recordRepository;

    private final UserRecordRepository userRecordRepository;
    private final PaymentGroupRepository paymentGroupRepository;


    @Autowired
    public RecordServiceImp(RecordRepository recordRepository, UserRecordRepository userRecordRepository, PaymentGroupRepository paymentGroupRepository) {
        this.recordRepository = recordRepository;
        this.userRecordRepository = userRecordRepository;
        this.paymentGroupRepository = paymentGroupRepository;

    }


    @Override
    public List<RecordBody> getAllRecordByUsersAndHouse(Integer userId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByParticipants(userId, houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getRecordByHouseForAllMember(String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByHouseForAllMember(houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getAllRecordByPayerAndHouse(Integer payerId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByPayer_IdAndHouse_Id(payerId, houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getRecordByPayerAndGroup(Integer payerId, Integer groupId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByPayer_IdAndPaymentGroupAndHouse_Id(payerId, groupId, houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getRecordByPayerAndHouse(Integer payerId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByPayer_IdAndPaymentGroupAndHouse_Id(payerId, 0, houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getRecordByUsersAndOther(Integer userId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByParticipantsAndPaymentGroup(userId, houseId, -1, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public List<RecordBody> getRecordByPayerAndOther(Integer payerId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByPayer_IdAndPaymentGroupAndHouse_Id(payerId, -1, houseId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    @Override
    public void saveRecord(RecordBody recordBody, String id) {
        Record record = getRecord(recordBody, id);
        record.setIsRemoved(false);
        recordRepository.save(record);
        for (Integer participantId : recordBody.getParticipantIds()) {
            UserRecord userRecord = new UserRecord();
            UserRecordId userRecordId = new UserRecordId();
            if (!Objects.equals(id, "")) {
                userRecordId.setRecordId(id);
                userRecordId.setParticipantId(participantId);
            }
            userRecord.setId(userRecordId);
            User user = new User();
            user.setId(participantId);
            userRecord.setParticipant(user);
            userRecord.setRecord(record);
            userRecordRepository.save(userRecord);
        }
    }

    private Record getRecord(RecordBody recordBody, String id) {
        Record record = new Record();

        record.setId(id);
        record.setMoney(recordBody.getMoney());
        record.setDate(recordBody.getDate());
        record.setInformation(recordBody.getInformation());
        record.setPaymentGroup(recordBody.getPaymentGroup());
        record.setPaid(recordBody.isPaid());
        User payer = new User();
        payer.setId(recordBody.getPayer().getId());
        House house = new House();
        house.setId(recordBody.getHouseId());
        record.setPayer(payer);
        record.setHouse(house);
        return record;
    }

    @Override
    public List<Object> findDateOfRecords(String houseId) {
        return recordRepository.findDateOfRecord(houseId);
    }

    @Override
    public Integer findPaidMoneyByDate(Integer userId, String houseId, String year, String month) {
        return recordRepository.findPaidMoneyByDate(userId, houseId, year, month) == null ? 0 : recordRepository.findPaidMoneyByDate(userId, houseId, year, month);
    }

    @Override
    public Integer findDebtMoneyByDate(Integer userId, String houseId, String year, String month) {
        return recordRepository.findDebtMoneyByDate(userId, houseId, year, month) == null ? 0 : recordRepository.findDebtMoneyByDate(userId, houseId, year, month);
    }

    @Override
    public RecordBody getRecordById(String id) {

        return RecordMapper.toRecordBody(recordRepository.getRecordById(id));
    }

    @Override
    public RecordBody getRemovedRecordById(String id) {
        return RecordMapper.toRecordBody(recordRepository.getRemovedRecordById(id));
    }

    @Override
    public void removeRecordById(String id) {
        recordRepository.removeRecordById(id);
    }

    //
    @Override
    public List<Map<String, Object>> getTotalFeeByMonth(String houseId, Integer userId) {
        return recordRepository.getTotalFeeByMonth(houseId, userId);
    }

    @Override
    public List<Map<String, Object>> getTotalPaidByMonth(String houseId, Integer userId) {
        return recordRepository.getTotalPaidByMonth(houseId, userId);
    }

    @Override
    public List<Map<String, Object>> getPaidForByMonth(String houseId, Integer userId, YearMonth date) {
        List<Map<String, Object>> data = new ArrayList<>(recordRepository.getPaidForByMonth(houseId, userId, date.toString()));
        return getMaps(data);
    }

    private List<Map<String, Object>> getMaps(List<Map<String, Object>> data) {
        List<Map<String, Object>> temp = new ArrayList<>();
        for (Map<String, Object> map : data) {
            Map<String, Object> modifiableMap = new HashMap<>(map);
            int id = Integer.parseInt(modifiableMap.get("paymentGroup").toString());
            String name = id == 0 ? "Nhà" : id == -1 ? "Khác" : paymentGroupRepository.getPaymentGroupById(id).getName();
            modifiableMap.put("paymentGroupName", name);
            temp.add(modifiableMap);
        }
        return temp;
    }

    @Override
    public List<Map<String, Object>> getFeeFromByMonth(String houseId, Integer userId, YearMonth date) {
        List<Map<String, Object>> data = new ArrayList<>(recordRepository.getFeeFromByMonth(houseId, userId, date.toString()));
        return getMaps(data);
    }



    //
    @Override
    public List<RecordBody> getRecordByUsersAndGroup(Integer userId, Integer groupId, String houseId, String year, String month) {
        List<RecordBody> recordBodyList = new ArrayList<>();
        for (Record record : recordRepository.findRecordsByParticipantsAndPaymentGroup(userId, houseId, groupId, year, month)) {
            recordBodyList.add(RecordMapper.toRecordBody(record));
        }
        return recordBodyList;
    }

    //

    @Override
    public List<Map<String, Object>> getSummaryByMonth(String houseId, YearMonth date) {
        List<Map<String,Object>> needInPut = recordRepository.getSummaryFee(houseId,date.toString());
        List<Map<String,Object>> paidInPut = recordRepository.getSummaryPaid(houseId,date.toString());
        Map<Integer,Double> need = new HashMap<>();
        Map<Integer,Double> paid = new HashMap<>();
        Map<Integer,String> users = new HashMap<>();
        for(Map<String,Object> needEle:paidInPut){
            Integer tempId = (Integer) needEle.get("id");
            paid.put(tempId, Double.parseDouble(needEle.get("paid").toString()));
        }
        for(Map<String,Object> needEle:needInPut){
            Integer tempId = (Integer) needEle.get("id");

            users.put(tempId,needEle.get("username").toString());
            need.put(tempId, Double.parseDouble(needEle.get("totalFee").toString()));
            paid.putIfAbsent(tempId, 0.0);
        }
       return Algorithm.calculateMoneyTransfer(need,paid,users);
    }
}

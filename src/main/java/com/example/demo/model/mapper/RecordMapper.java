package com.example.demo.model.mapper;

import com.example.demo.entity.Record;

import com.example.demo.entity.UserRecord;
import com.example.demo.model.dto.RecordBody;


import java.util.HashSet;
import java.util.Set;

public class RecordMapper {
    public static RecordBody toRecordBody(Record record) {
        if (record != null) {
            Set<Integer> participantIds = new HashSet<>();
            for (UserRecord participant : record.getParticipants()) {
                participantIds.add(participant.getParticipant().getId());
            }
            return new RecordBody(record.getId(), record.getMoney(), record.getDate(), record.getInformation(), record.getPaymentGroup(), record.isPaid(), UserMapper.toUserDto(record.getPayer()), record.getHouse().getId(), participantIds);
        }
        else return null;
    }
}
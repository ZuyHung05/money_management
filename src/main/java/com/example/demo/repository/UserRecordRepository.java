package com.example.demo.repository;

import com.example.demo.entity.UserRecord;
import com.example.demo.serializable.UserRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordRepository extends JpaRepository<UserRecord, UserRecordId> {

}

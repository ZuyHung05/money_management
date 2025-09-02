package com.example.demo.serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRecordId implements Serializable {
    @Column(name = "record_id")
    String recordId;
    @Column(name="participant_id")
    Integer participantId;
}

package com.example.demo.entity;

import com.example.demo.serializable.UserRecordId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "user_record")

public class UserRecord {
    @EmbeddedId
    UserRecordId id;
    @JsonIgnore
    @ManyToOne
    @MapsId("recordId")
    @JoinColumn(name = "record_id")
    private Record record;
    @JsonIgnore
    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private User participant;
}

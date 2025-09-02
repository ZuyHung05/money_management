package com.example.demo.entity;

import com.example.demo.serializable.UserGroupId;
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
@Table(name = "user_group")
public class UserGroup {
    @EmbeddedId
    UserGroupId id;
    @JsonIgnore
    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private PaymentGroup paymentGroup;
    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private User user;
}

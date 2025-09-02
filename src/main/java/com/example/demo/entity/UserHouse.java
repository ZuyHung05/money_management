package com.example.demo.entity;

import com.example.demo.serializable.UserHouseId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "user_house")
public class UserHouse {
    @EmbeddedId
    UserHouseId id;
    @JsonIgnore
    @ManyToOne
    @MapsId("houseId")
    @JoinColumn(name = "house_id")
    private House house;
    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private User user;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private boolean userRole;
}

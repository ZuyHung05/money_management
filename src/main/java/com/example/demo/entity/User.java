package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String deviceToken;
    @OneToMany(mappedBy = "user")
    private Set<UserGroup> paymentGroups;
    @OneToMany(mappedBy = "participant")
    private Set<UserRecord> records;
    @OneToMany(mappedBy = "user")
    private Set<UserHouse> userHouses;
    @OneToMany(mappedBy = "user")
    private List<UserNotification> notifications;
}

package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordBody {
    private String id;

    private long money;
    private LocalDate date;
    private String information;
    private int paymentGroup;
    private boolean paid;
    private UserDTO payer;
    private String houseId;

    private Set<Integer> participantIds;
}

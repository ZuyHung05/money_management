package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGroupBody {
    private Integer id;
    private String houseId ;
    private String name ;
    List<Integer> userIds;
}

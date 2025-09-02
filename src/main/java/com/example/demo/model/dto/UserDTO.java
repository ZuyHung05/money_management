package com.example.demo.model.dto;


import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String email;
}
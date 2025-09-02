package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Login")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{email}")
    public ResponseEntity<Boolean> isEmailExisted(@PathVariable String email) {
        return ResponseEntity.ok(userService.isEmailExist(email));
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<Boolean> isPasswordMatch(@PathVariable String email, @PathVariable String password) {
        return ResponseEntity.ok(passwordEncoder.matches(password, userService.getUserByEmail(email).getPassword()));
    }
}


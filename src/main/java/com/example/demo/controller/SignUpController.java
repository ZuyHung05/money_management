package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/SignUp")
public class SignUpController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/{email}")
    public ResponseEntity<Boolean> isEmailExisted(@PathVariable String email){
        return ResponseEntity.ok(userService.isEmailExist(email));
    }
    @PostMapping("/success")
    public void signup(@RequestBody User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
    }
}

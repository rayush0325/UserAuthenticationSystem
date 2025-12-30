package com.learning.UserAuthenticationSystem.controller;

import com.learning.UserAuthenticationSystem.model.User;
import com.learning.UserAuthenticationSystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        return userService.authenticateUser(user);
    }
}

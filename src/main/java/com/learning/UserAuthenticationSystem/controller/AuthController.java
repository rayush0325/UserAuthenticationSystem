package com.learning.UserAuthenticationSystem.controller;

import com.learning.UserAuthenticationSystem.dtos.LoginRequest;
import com.learning.UserAuthenticationSystem.dtos.RegisterRequest;
import com.learning.UserAuthenticationSystem.model.User;
import com.learning.UserAuthenticationSystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        return userService.saveUser(registerRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.authenticateUser(loginRequest);
    }
}

package com.learning.UserAuthenticationSystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class UserController {
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String greetUser(){
        return "Hello user";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String greetAdmin(){
        return "Hello admin";
    }
}

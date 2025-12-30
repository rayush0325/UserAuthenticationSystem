package com.learning.UserAuthenticationSystem.dtos;

public class LoginResponse {
    private  String jwt;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

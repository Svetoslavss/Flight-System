package com.academy.flightsystem.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Long expiresIn;
    private String username;
    private List<String> roles;
    private String error;


    public  LoginResponse(String username, String token){
         this.username = username;
         this.token = token;
    }

    public LoginResponse setToken(String token){
        this.token = token;
        return this;
    }

    public LoginResponse setExpiresIn(Long expiresIn){
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponse setRole(List<String> role){
        this.roles = role;
        return this;
    }

    public LoginResponse setError(String error){
        this.error = error;
        return this;
    }
}

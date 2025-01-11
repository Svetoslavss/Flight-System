package com.academy.flightsystem.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Long expiresIn;
    private String username;
    private String error;
    private List<String> roles;

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

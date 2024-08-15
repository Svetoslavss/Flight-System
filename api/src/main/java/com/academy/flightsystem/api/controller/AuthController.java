package com.academy.flightsystem.api.controller;

import com.academy.flightsystem.api.model.dto.LoginUserDto;
import com.academy.flightsystem.api.model.dto.RegisterUserDto;
import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserInfo register(@RequestBody RegisterUserDto user){
        return authService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginUserDto user){
        return authService.login(user);
    }
}

package com.academy.flightsystem.api.controller;

import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    // Authentication manager
    // jwtUtil
    // repo + service

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public UserInfo register(@RequestBody UserInfo userInfo){
        return service.register(userInfo);
    }

}

package com.academy.flightsystem.api.controller;

import com.academy.flightsystem.api.exceptions.BadRequestException;
import com.academy.flightsystem.api.exceptions.ProfileNotFoundException;
import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.response.ApiResponse;
import com.academy.flightsystem.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserInfo user){
        try {
            UserInfo registeredUser = service.register(user);
            return ResponseEntity.ok(new ApiResponse("Successfully registered", registeredUser));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(){
       try {
           UserInfo userProfile = service.findProfile();
           return ResponseEntity.ok(new ApiResponse("Successfully found profile", userProfile));
       } catch (ProfileNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }
}

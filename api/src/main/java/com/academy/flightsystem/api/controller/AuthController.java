package com.academy.flightsystem.api.controller;

import com.academy.flightsystem.api.exceptions.BadRequestException;
import com.academy.flightsystem.api.exceptions.InvalidCredentialsException;
import com.academy.flightsystem.api.model.LoginResponse;
import com.academy.flightsystem.api.model.dto.LoginUserDto;
import com.academy.flightsystem.api.model.dto.RegisterUserDto;
import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.response.ApiResponse;
import com.academy.flightsystem.api.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.academy.flightsystem.api.utils.CodeEN.INVALID_CREDENTIAL_EXCEPTION;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterUserDto user) {
        try {
            UserInfo register = authService.register(user);
            return ResponseEntity.ok(new ApiResponse("success", register));
        } catch (BadRequestException e) {
            logger.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Internal server error"));
        }
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)// specify JSON as the expected request body content type)
    public ResponseEntity<ApiResponse> login(@RequestBody LoginUserDto user) {
        logger.info("Hit login endpoint: {}", user);
        try {
         LoginResponse response = authService.login(user);
         return ResponseEntity.ok(new ApiResponse("success", response));
     } catch (InvalidCredentialsException e){
         logger.error("Invalid credentials: {}", e.getMessage());
         return ResponseEntity.badRequest().body(new ApiResponse("error",new LoginResponse().setError(INVALID_CREDENTIAL_EXCEPTION.toString())));
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Internal server error"));
     }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> admin(){
        try {
            return ResponseEntity.ok(new ApiResponse("Admin role present", "Admin endpoint accessed"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Internal server error"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> userEndpoint(){
        try {
            return ResponseEntity.ok(new ApiResponse("User role present", "User endpoint accessed"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", "Internal server error"));
        }
    }

}

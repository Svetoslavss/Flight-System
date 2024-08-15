package com.academy.flightsystem.api.service;

import com.academy.flightsystem.api.model.dto.LoginUserDto;
import com.academy.flightsystem.api.model.dto.RegisterUserDto;
import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.repository.UserRepository;

import com.academy.flightsystem.api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserInfo register(RegisterUserDto registerUserDto){
     UserInfo userInfo = new UserInfo();
     userInfo.setUsername(registerUserDto.getUsername());
     userInfo.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
     return repository.save(userInfo);
    }

    // loginResponse(token, user)

        public String login(LoginUserDto userDto){
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

         var token = jwtService.generateToken(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())).getPrincipal().toString());
         var user = repository.findByUsername(userDto.getUsername()).orElseThrow();

         return token + " " + user.getUsername();
        }
}

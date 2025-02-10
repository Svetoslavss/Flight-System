package com.academy.flightsystem.api.service;

import com.academy.flightsystem.api.model.UserInfo;
import com.academy.flightsystem.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

        public UserInfo register(UserInfo userInfo) {
            return userRepository.save(userInfo);
        }

    public UserInfo findProfile() {
        return userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

package com.academy.flightsystem.client.service;

import com.academy.flightsystem.client.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FlightService {

    @Value("$backend.api.url")
    private String backendApiUrl;

    @Autowired
    RestTemplate restTemplate;

    public List<Flight> getAllFlights(){
        Flight[] flights = restTemplate.getForObject(backendApiUrl + "/flights", Flight[].class);
        return Arrays.asList(Objects.requireNonNull(flights));
    }

    // public Flight getFlight by(id){}
}

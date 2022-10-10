package com.project.passengermanagementmicroservice.service;

import com.project.passengermanagementmicroservice.customexception.PassengerAlreadyExistException;
import com.project.passengermanagementmicroservice.customexception.PassengerNotFoundException;
import com.project.passengermanagementmicroservice.model.Passenger;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface PassengerService extends UserDetailsService {
    public Passenger registerPassenger(Passenger passenger) throws PassengerAlreadyExistException;

    public Optional<Passenger> viewPassenger(String email) throws PassengerNotFoundException;

    public boolean deletePassenger(int id) throws PassengerNotFoundException;

    public Passenger updatePassenger(int id,Passenger passenger) throws PassengerNotFoundException;
}

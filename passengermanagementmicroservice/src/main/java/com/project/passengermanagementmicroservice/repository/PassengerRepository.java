package com.project.passengermanagementmicroservice.repository;

import com.project.passengermanagementmicroservice.model.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PassengerRepository extends MongoRepository<Passenger,Integer> {

    @Query("{passengerEmail:?0}")
    Passenger findByEmail(String email);


    @Query("{passengerEmail:?0}")
    Passenger existsByEmail(String email);
}

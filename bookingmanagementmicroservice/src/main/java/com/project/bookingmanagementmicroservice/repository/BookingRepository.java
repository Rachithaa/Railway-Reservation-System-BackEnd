package com.project.bookingmanagementmicroservice.repository;

import com.project.bookingmanagementmicroservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking,Integer> {
   @Query("{trainNumber:?0}")
    public List<Booking> findByTrainId(int trainId);
}

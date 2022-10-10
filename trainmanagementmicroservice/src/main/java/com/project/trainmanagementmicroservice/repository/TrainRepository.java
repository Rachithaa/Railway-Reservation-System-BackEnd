package com.project.trainmanagementmicroservice.repository;

import com.project.trainmanagementmicroservice.model.Train;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TrainRepository extends MongoRepository<Train, Integer> {



    @Query("{'route.stationName':?0,'route.stationName':?1,daysOfRunning:?2}")
    List<Train> search(String source, String destination, String s);
}

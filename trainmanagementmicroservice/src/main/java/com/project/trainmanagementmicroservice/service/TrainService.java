package com.project.trainmanagementmicroservice.service;

import com.project.trainmanagementmicroservice.customexception.JourneyDateInvalidException;
import com.project.trainmanagementmicroservice.customexception.TrainAlreadyExistException;
import com.project.trainmanagementmicroservice.customexception.TrainNotFoundException;
import com.project.trainmanagementmicroservice.model.Train;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface TrainService {

    public Train addTrain(Train train) throws TrainAlreadyExistException;

    public List<Train> listAllTrain() throws TrainNotFoundException;

    public Optional<Train> listTrain(int id) throws TrainNotFoundException;

    public Train updateTrain(int id,Train train) throws TrainNotFoundException;

    public boolean deleteTrain(int id) throws TrainNotFoundException;


   public List<Train> searchTrain(String source, String destination,String journeydate) throws ParseException,TrainNotFoundException, JourneyDateInvalidException;
}

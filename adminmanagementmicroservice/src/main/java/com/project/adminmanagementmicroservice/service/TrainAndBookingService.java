package com.project.adminmanagementmicroservice.service;

import com.project.adminmanagementmicroservice.model.Booking;
import com.project.adminmanagementmicroservice.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TrainAndBookingService {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity addTrain(Train train) throws HttpClientErrorException {
        try {
            Train savedTrain = restTemplate.postForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/addTrain", train, Train.class);
            return new ResponseEntity<>(savedTrain, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(),HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Train[]> listAllTrain() throws HttpClientErrorException
    {
        try {
            Train[] getTrain = restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/alllist", Train[].class);
            return new ResponseEntity<Train[]>(getTrain, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity listTrain(int trainId) {
        try {
            Train getTrain=restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/listTrain/"+trainId, Train.class);
            return new ResponseEntity(getTrain, HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity updateTrain(int trainId, Train train)
    {
        try {
        restTemplate.put("http://TRAIN-MANAGEMENT-SERVICE/trains/update/"+trainId,train);
        return new ResponseEntity(HttpStatus.OK);
    }
        catch (HttpClientErrorException httpClientErrorException) {
        return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
    }
}

    public ResponseEntity deleteTrain(int trainId) {
        try {
             restTemplate.delete("http://TRAIN-MANAGEMENT-SERVICE/trains/delete/"+trainId,Train.class);
                return new ResponseEntity(HttpStatus.OK);

        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Booking[]> getBookingDetails(int trainId) {
        try {
            Booking[] getDetails=restTemplate.getForObject("http://BOOKING-MANAGEMENT-SERVICE/booking/bookingdetails/"+trainId, Booking[].class);
            return new ResponseEntity<Booking[]>(getDetails,HttpStatus.OK);

        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }
}

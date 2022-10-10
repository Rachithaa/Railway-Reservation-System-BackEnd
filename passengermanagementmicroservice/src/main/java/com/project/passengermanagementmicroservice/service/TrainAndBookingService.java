package com.project.passengermanagementmicroservice.service;

import com.project.passengermanagementmicroservice.model.Booking;
import com.project.passengermanagementmicroservice.model.Train;
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
    public ResponseEntity<Train[]> searchTrain(String source, String destination, String journeydate) {
        try {
            Train[] getTrain =restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/search/"+source+"/"+destination+"/"+journeydate, Train[].class);
            return ResponseEntity.ok().body(getTrain);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Train> getTrain(int trainid) {
        try {
            Train getTrain =restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/listTrain/"+trainid, Train.class);
            return ResponseEntity.ok().body(getTrain);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }


    public ResponseEntity saveBooking(Booking booking, int trainId, String passengerId, String source, String destination, String journeydate) {

        try {
            Booking savedBooking = restTemplate.postForObject("http://BOOKING-MANAGEMENT-SERVICE/booking/savebooking/"+trainId+"/"+passengerId+"/"+source+"/"+destination+"/"+journeydate,booking,Booking.class);

            return ResponseEntity.ok().body(savedBooking);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity getTicket(int bookingId)
    {
        try {

            Booking getTicket=restTemplate.getForObject("http://BOOKING-MANAGEMENT-SERVICE/booking/ticket/"+bookingId,Booking.class);
            return ResponseEntity.ok().body(getTicket);
           // return new ResponseEntity(getTicket,HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            return new ResponseEntity(httpClientErrorException.getResponseBodyAsString(), HttpStatus.CONFLICT);
        }
    }
}

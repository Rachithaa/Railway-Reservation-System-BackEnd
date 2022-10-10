package com.project.bookingmanagementmicroservice.resource;


import com.project.bookingmanagementmicroservice.customexception.BookingNotFoundException;
import com.project.bookingmanagementmicroservice.customexception.InvalidSeatException;
import com.project.bookingmanagementmicroservice.customexception.TrainNotFoundException;
import com.project.bookingmanagementmicroservice.model.Booking;
import com.project.bookingmanagementmicroservice.service.BookingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;


@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @ApiOperation(value = "booking the train ",notes = "books the train on the basis of source,destination along passenger,train and journey date",response = Booking.class)
    @PostMapping("/savebooking/{trainId}/{passengerEmail}/{source}/{destination}/{journeydate}")
    public ResponseEntity saveBooking(@RequestBody @Valid Booking booking, @PathVariable("trainId") int trainId,
                                      @PathVariable("passengerEmail") String passengerEmail, @PathVariable("source") String source,
                                      @PathVariable("destination") String destination, @PathVariable("journeydate") String journeydate) throws ParseException, InvalidSeatException {
        try
        {
            log.info("booking");
            return  new ResponseEntity(bookingService.saveBooking(booking,trainId,passengerEmail,source,destination,journeydate), HttpStatus.OK);
        }
        catch(InvalidSeatException invalidSeatException){
            log.error("not able to book");
            return new ResponseEntity("The seats entered are invalid ", HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "passengers to get the ticket",notes = "passenger can the booking ticket through booking id",response = Booking.class)
    @GetMapping("/ticket/{bookingId}")
    public ResponseEntity getTicket(@PathVariable("bookingId") int bookingId) throws BookingNotFoundException
    {
        try
        {
            log.info("get the ticket");
            return  new ResponseEntity(bookingService.getTicket(bookingId), HttpStatus.OK);
        }
        catch(BookingNotFoundException bookingNotFoundException){
            log.error("not able to get the ticket");
            return new ResponseEntity("The booking doesnot exists ", HttpStatus.CONFLICT);
        }

    }

    @ApiOperation(value = "admin get the to the passengers from train number(train id)",notes = "admin get the to know the passengers who booked from train number(train id)",response = Booking.class)
    @GetMapping("/bookingdetails/{trainId}")
    public ResponseEntity getBookingDetails(@PathVariable("trainId") int trainId) throws TrainNotFoundException
    {
        try
        {
            log.info("get the booking details by train id");
            return  new ResponseEntity(bookingService.getBookingDetails(trainId), HttpStatus.OK);
        }
        catch(BookingNotFoundException bookingNotFoundException){
            log.error("cannot get the booking details");
            return new ResponseEntity("The booking details for "+trainId+" train number  does not exists ", HttpStatus.CONFLICT);
        }
    }
}

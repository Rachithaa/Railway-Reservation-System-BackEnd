package com.project.passengermanagementmicroservice.resource;

import com.project.passengermanagementmicroservice.customexception.PassengerNotFoundException;
import com.project.passengermanagementmicroservice.model.Booking;
import com.project.passengermanagementmicroservice.model.Passenger;
import com.project.passengermanagementmicroservice.model.Train;
import com.project.passengermanagementmicroservice.service.PassengerService;
import com.project.passengermanagementmicroservice.service.TrainAndBookingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/passenger")
@Slf4j
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    TrainAndBookingService trainAndBookingService;

  /*  @ApiOperation(value = "Register the passenger",notes = "All the details of passengers gets register",response = Passenger.class)
    @PostMapping("/passengerregister")
    public ResponseEntity registerPassenger(@Valid @RequestBody Passenger passenger) throws PassengerAlreadyExistException
    {
        try {
            Passenger savePassenger =  passengerService.registerPassenger(passenger);
            return new ResponseEntity<>(savePassenger, HttpStatus.OK);
        }
        catch(PassengerAlreadyExistException passengerAlreadyExistException){
            return new ResponseEntity("The passenger with "+passenger.getPassengerId()+" details already exist", HttpStatus.CONFLICT);
        }

    }*/
    @ApiOperation(value = "view passenger by id",notes = "passenger can view his details by id",response = Passenger.class)
    @GetMapping("/viewpassenger/{passengerEmail}")
    public ResponseEntity viewPassenger(@PathVariable("passengerEmail") String passengerEmail)
    {
        try {
            log.info("view passenger detail");
            return new ResponseEntity(passengerService.viewPassenger(passengerEmail), HttpStatus.OK);
        }
        catch(PassengerNotFoundException passengerNotFoundException){
            log.error("cannot view passenger detail");
            return new ResponseEntity("The passenger with passenger email: "+passengerEmail+" does not exists", HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "delete passenger by id", notes = "passenger can delete the account by id",response =Passenger.class)
    @DeleteMapping("/deletepassenger/{passengerId}")
    public ResponseEntity deletePassenger(@PathVariable("passengerId") int passengerId)
    {
        try {
            log.info("delete passenger");
            var isdel = passengerService.deletePassenger(passengerId);
            if (isdel) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        catch(PassengerNotFoundException passengerNotFoundException){
            log.error("cannot delete the passenger");
            return new ResponseEntity("The passenger with passenger id: "+passengerId+" does not exists", HttpStatus.CONFLICT);
        }
        return null;
    }

    @ApiOperation(value = "update passenger by id",notes = "update the required details of passenger by id",response = Passenger.class)
    @PutMapping("/updatepassenger/{passengerId}")
    public ResponseEntity updatePassenger(@PathVariable("passengerId") int id,@Valid @RequestBody Passenger passenger)
    {
        try
        {
            log.info("update the passenger");
            return  new ResponseEntity(passengerService.updatePassenger(id,passenger),HttpStatus.OK);
        }
        catch(PassengerNotFoundException passengerNotFoundException){
            log.error("cannot update the passenger");
            return new ResponseEntity("The passenger with passenger id: "+id+" does not exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/passengersearchtrain")
    @ApiOperation(value = "passengers search by source,destination,journey date",notes="search a particular train details based on the source,destination,journey date",response = Train.class)
    public ResponseEntity searchTrain(@RequestParam String source,@RequestParam  String destination,@RequestParam String journeydate )
    {
        log.info("seach the train by passenger on basis of source,destination,journey date");
       return trainAndBookingService.searchTrain(source,destination,journeydate);
    }

    @ApiOperation(value = "booking the train ",notes = "books the train on the basis of source,destination along passenger,train and journey date",response = Booking.class)
    @PostMapping("/passengersavebooking")
    public ResponseEntity saveBooking(@RequestBody @Valid Booking booking,@RequestParam int trainId,
                                      @RequestParam String passengerEmail, @RequestParam String source,
                                      @RequestParam String destination, @RequestParam String journeydate)
    {
        log.info("booking for the train by passenger");
        return  trainAndBookingService.saveBooking(booking,trainId,passengerEmail,source,destination,journeydate);
    }

    @ApiOperation(value = "passengers to get the ticket",notes = "passenger can the booking ticket through booking id",response = Booking.class)
    @GetMapping("/passengersticket")
    public ResponseEntity getTicket(@RequestParam("bookingId") int bookingId)
    {
            log.info("view ticket by passenger");
            return trainAndBookingService.getTicket(bookingId);

    }
    @ApiOperation(value = "get trains from passengers ",notes = "passenger can view train details",response = Train.class)
    @GetMapping("/gettrain/{trainId}")
    public ResponseEntity getTrain(@PathVariable("trainId") int trainId)
    {
        log.info("get the train from passengers");
        return trainAndBookingService.getTrain(trainId);

    }

}

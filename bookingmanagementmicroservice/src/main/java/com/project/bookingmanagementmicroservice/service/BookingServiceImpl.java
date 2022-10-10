package com.project.bookingmanagementmicroservice.service;

import com.project.bookingmanagementmicroservice.customexception.InvalidSeatException;
import com.project.bookingmanagementmicroservice.model.*;
import com.project.bookingmanagementmicroservice.repository.BookingRepository;
import com.project.bookingmanagementmicroservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Booking saveBooking(Booking booking, int trainId, String passengerEmail, String source, String destination,
                               String journeydate) throws ParseException {

        Train getTrain = restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/listTrain/" + trainId, Train.class);
        Passenger getPassenger = restTemplate.getForObject("http://PASSENGER-MANAGEMENT-SERVICE/passenger/viewpassenger/" + passengerEmail, Passenger.class);
        List<TrainClasses> trainClasses1 = getTrain.getTrainClasses();
        System.out.println(trainClasses1);
        String selectedClass = booking.getTrainClass();
        System.out.println(selectedClass);

        final int[] numseats3 = new int[1];
        trainClasses1.forEach(iterator -> {
            if (iterator.getClassName().equals(selectedClass)) {
                numseats3[0] = iterator.getNumOfSeats();
            }
        });

        if(booking.getTotalNumOfSeats()<1 || booking.getTotalNumOfSeats()>6)
       {
           throw new InvalidSeatException();
       }
       if(getTrain.getTotalNumOfSeats()<1)
       {
           throw new InvalidSeatException();
       }
        if(numseats3[0]- booking.getTotalNumOfSeats()<1)
        {
            throw new InvalidSeatException();
        }
       else{
           booking.setBookingId(sequenceGeneratorService.getSequenceNumber(Booking.SEQUENCE_NAME));
           SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
           String today = df.format(new Date());
           booking.setSource(source);
           booking.setDestination(destination);
           booking.setJourneyDate(journeydate);
           booking.setCurrentDate(today);
           booking.setTrainNumber(trainId);
           booking.setTrainName(getTrain.getTrainName());
           booking.setPassengerId(getPassenger.getPassengerId());
           booking.setPassengerName(getPassenger.getPassengerName());
           booking.setPassengerEmail(passengerEmail);
           booking.setPassengerPhone(getPassenger.getPassengerPhone());


           final Double[] classPrice = new Double[1];
           trainClasses1.forEach(iterator -> {
               if (iterator.getClassName().equals(selectedClass)) {
                   classPrice[0] = iterator.getPrice();

               }
           });
           System.out.println(classPrice[0]);

           List<Route> trainRoute = getTrain.getRoute();
           System.out.println(trainRoute);
           final double[] totaldistance = new double[1];
           final String[] sourcearrival = new String[1];
           final String[] sourcedeparture = new String[1];
           final String[] destinationarrival = new String[1];
           trainRoute.forEach(iterator -> {
               if (iterator.getStationName().equals(source)) {
                   totaldistance[0] = iterator.getTotalDistance();
                   sourcearrival[0] = iterator.getTimeOfArrival();
                   sourcedeparture[0] = iterator.getTimeOfDeparture();

               }
               if (iterator.getStationName().equals(destination)) {
                   destinationarrival[0] = iterator.getTimeOfArrival();
               }
           });
           System.out.println(totaldistance[0]);
           //System.out.println(sourcearrival[0]);
           booking.setSourcetimeOfArrival(sourcearrival[0]);
           // System.out.println(sourcedeparture[0]);
           booking.setSourcetimeOfDeparture(sourcedeparture[0]);
           //System.out.println(destinationarrival[0]);
           booking.setDestinationtimeOfArrival(destinationarrival[0]);
           System.out.println(getTrain.getPricePerKms());

           double price = (getTrain.getPricePerKms() * totaldistance[0]) + classPrice[0];
           double overallprice = booking.getTotalNumOfSeats() * price;
           // System.out.println(classPrice[0]);
           // System.out.println(overallprice);
           booking.setPrice(Double.parseDouble(String.format("%.2f",overallprice)));
           //______________________________________________________________________
           int numseats1 = booking.getTotalNumOfSeats();
           // Train getTrain1=restTemplate.getForObject("http://TRAIN-MANAGEMENT-SERVICE/trains/listTrain/"+trainId, Train.class);
           int numseats2 = getTrain.getTotalNumOfSeats();
           getTrain.setTotalNumOfSeats(numseats2 - numseats1);
           //  restTemplate.put("http://TRAIN-MANAGEMENT-SERVICE/trains/update/"+getTrain.getTrainId(),getTrain);


           int totalseatfortraincls = numseats3[0] - numseats1;
           trainClasses1.forEach(iterator -> {
               if (iterator.getClassName().equals(selectedClass)) {
                   iterator.setNumOfSeats(totalseatfortraincls);
               }
           });
           restTemplate.put("http://TRAIN-MANAGEMENT-SERVICE/trains/update/" + getTrain.getTrainId(), getTrain);
           return bookingRepository.save(booking);
       }

    }

    @Override
    public Optional<Booking> getTicket(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> getBookingDetails(int trainId) {
        return bookingRepository.findByTrainId(trainId);
    }
}


package com.project.bookingmanagementmicroservice.service;

import com.project.bookingmanagementmicroservice.customexception.BookingNotFoundException;
import com.project.bookingmanagementmicroservice.customexception.InvalidSeatException;
import com.project.bookingmanagementmicroservice.customexception.TrainNotFoundException;
import com.project.bookingmanagementmicroservice.model.Booking;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    public Booking saveBooking(Booking booking, int trainId, String passengerEmail, String source, String destination,
                               String journeydate) throws ParseException, InvalidSeatException;

   public Optional<Booking> getTicket(int bookingId) throws BookingNotFoundException;

   public List<Booking> getBookingDetails(int trainId) throws TrainNotFoundException;
}

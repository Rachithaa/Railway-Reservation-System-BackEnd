package com.project.bookingmanagementmicroservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Booking")
@ApiModel(description = "Details about the Booking")
public class Booking {
    @Transient
    public static final String SEQUENCE_NAME= "booking_sequence";

    @Id
    private int bookingId;

    private int passengerId;


   // @NotEmpty(message = "required")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$",message = "Only digits are allowed for date in the format dd-mm-yyyy")
    private String currentDate;

    @Digits(integer = 5,fraction = 0)
    private int trainNumber;

    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String trainName;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "Only alphabets and spaces are allowed is allowed")
    @Size(min = 3, max = 15,message = "min 3 max 15")
    private String passengerName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,20}$",message = "It should be in format abc@gmail.com")
    private String passengerEmail;

    @Pattern(regexp = "^\\d{10}$",message = "It should be of 10 digits")
    private String passengerPhone;


    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$",message = "Only digits are allowed for date in the format dd-mm-yyyy")
    private String journeyDate;

    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String source;

    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String destination;

    @Digits(integer = 4,fraction = 2)
    private double price;

    @NotEmpty(message = "required")
    @Pattern(regexp ="^[A-Za-z0-9 -]+$",message = "Only alphabets and digits allowed along -. No special characters allowed")
    @Size(min = 3, max = 20,message = "Only alphabets and digits allowed along -. No special characters allowed")
    private String trainClass;

    @NotNull(message = "required")
    @Digits(integer = 1,fraction = 0)
    private int totalNumOfSeats;

    @Valid
    private List<Passengers> passengers;


    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
    @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
    private String sourcetimeOfArrival;


   @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
   @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
   private String sourcetimeOfDeparture;

   @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
   @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
   private String destinationtimeOfArrival;


}

package com.project.bookingmanagementmicroservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Document(collection = "passenger")
@ApiModel(description = "Details about the Passenger")
public class Passenger {

    @Id
    private int passengerId;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "Only alphabets and spaces are allowed is allowed")
    @Size(min = 3, max = 15,message = "min 3 max 15")
    private String passengerName;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",message = "It contains at least 8 characters and at most 20 characters.\n" +
            "It contains at least one digit.\n" +
            "It contains at least one upper case alphabet.\n" +
            "It contains at least one lower case alphabet.\n" +
            "It contains at least one special character \n" +
            "It doesn’t contain any white space.")
    private String passengerPassword;


    @NotEmpty(message = "required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,20}$",message = "It should be in format abc@gmail.com")
    private String passengerEmail;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^\\d{10}$",message = "It should be of 10 digits")
    private String passengerPhone;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z0-9 \\-:/()]+$",message = "address can have alphabets and numbers and special character such as(-,:,/,())")
    @Size(min = 10, max = 50,message = "min 10 max 50")
    private String passengerAddress;
}

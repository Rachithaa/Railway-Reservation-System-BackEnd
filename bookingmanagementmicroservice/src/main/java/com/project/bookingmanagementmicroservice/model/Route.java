package com.project.bookingmanagementmicroservice.model;


import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details about the train route")
public class Route {
    @Id
    @Digits(integer = 2,fraction = 0)
    private int routeId;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String stationName;

    @NotEmpty
    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
    @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
    private String timeOfArrival;

    @NotEmpty
    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
    @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
    private String timeOfDeparture;

    @NotNull
    @Digits(integer = 4,fraction = 0)
    private double totalDistance;

}

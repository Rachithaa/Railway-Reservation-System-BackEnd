package com.project.trainmanagementmicroservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details about the train route")
public class Route {
    @Id
    @Digits(integer = 2,fraction = 0)
    @JsonProperty("routeId")
    private int routeId;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    @JsonProperty("stationName")
    private String stationName;

    @NotEmpty
    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
    @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
    @JsonProperty("timeOfArrival")
    private String timeOfArrival;

    @NotEmpty
    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$",message = "time should be in 00:00:00 format")
    @Size(min = 8, max = 8,message = "time should be in 00:00:00 format")
    @JsonProperty("timeOfDeparture")
    private String timeOfDeparture;

    @NotNull
    @Digits(integer = 4,fraction = 0)
    @JsonProperty("totalDistance")
    private double totalDistance;

    @JsonCreator
    public Route(@JsonProperty("routeId") int routeId,@JsonProperty("stationName") String stationName,@JsonProperty("timeOfArrival") String timeOfArrival,@JsonProperty("timeOfDeparture") String timeOfDeparture,@JsonProperty("totalDistance") double totalDistance) {
        this.routeId = routeId;
        this.stationName = stationName;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.totalDistance = totalDistance;
    }

}

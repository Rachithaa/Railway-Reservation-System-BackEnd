package com.project.trainmanagementmicroservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "Trains")
@ApiModel(description = "Details about the train")
public class Train {

    @Id
    @Digits(integer = 5,fraction = 0)
    private int trainId;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String trainName;


    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String source;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z -]+$",message = "Only alphabets and - allowed")
    @Size(min = 3, max = 20,message = "min 3 max 20")
    private String destination;


    @NotNull
    @Digits(integer = 1,fraction = 1)
    private double pricePerKms;


    @Valid
    @JsonProperty("route")
    private List<Route> route;

    @NotEmpty
    private String[] daysOfRunning;

    @NotNull
    @Digits(integer = 4,fraction = 0)
    private int totalNumOfSeats;

    @Valid
    @JsonProperty("trainClasses")
    private List<TrainClasses> trainClasses;

}

package com.project.adminmanagementmicroservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details about the Train Classes")
public class TrainClasses {

    @NotEmpty
    @Pattern(regexp ="^[A-Za-z0-9 -]+$",message = "Only alphabets and digits allowed along -. No special characters allowed")
    @Size(min = 3, max = 20,message = "Only alphabets and digits allowed along -. No special characters allowed")
    @JsonProperty("className")
    private String className;

    @NotNull
    @Digits(integer = 3,fraction = 2)
    @JsonProperty("price")
    private double price;

    @NotNull
    @Digits(integer = 4,fraction = 0)
    @JsonProperty("numOfSeats")
    private int numOfSeats;

    @JsonCreator
    public TrainClasses(@JsonProperty("className") String className,@JsonProperty("price") double price,@JsonProperty("numOfSeats") int numOfSeats) {
        this.className = className;
        this.price = price;
        this.numOfSeats = numOfSeats;
    }
}

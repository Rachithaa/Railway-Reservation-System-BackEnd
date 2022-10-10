package com.project.bookingmanagementmicroservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details of the train classes in train")
public class TrainClasses {

    @NotEmpty(message = "required")
    @Pattern(regexp ="^[A-Za-z0-9 -]+$",message = "Only alphabets and digits allowed along -. No special characters allowed")
    @Size(min = 3, max = 20,message = "Only alphabets and digits allowed along -. No special characters allowed")
    private String className;

    @NotNull(message = "required")
    @Digits(integer = 3,fraction = 2)
    private double price;

    @NotNull(message = "required")
    @Digits(integer = 4,fraction = 0)
    private int numOfSeats;
}

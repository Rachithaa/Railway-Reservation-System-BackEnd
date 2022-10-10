package com.project.adminmanagementmicroservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ApiModel(description = "Details about the additional passengers")
public class Passengers {

    @NotEmpty(message = "required")
    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "Only alphabets and spaces are allowed is allowed")
    @Size(min = 3, max = 15,message = "min 3 max 15")
    private String passengersName;

    @NotEmpty(message = "required")
    @Pattern(regexp = "^(?:Male|Female|Others)$",message = "Allowed Options are Male, Female & Others")
    private String passengersGender;

    @NotNull(message = "required")
    @Digits(integer = 3,fraction = 0)
    private int passengersAge;
}

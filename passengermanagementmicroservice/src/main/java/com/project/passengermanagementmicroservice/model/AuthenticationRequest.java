package com.project.passengermanagementmicroservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest {


    private String passengerEmail;

    private String passengerPassword;
}

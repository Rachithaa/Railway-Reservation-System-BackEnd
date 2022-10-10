package com.project.adminmanagementmicroservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest {


    private String adminEmail;

    private String adminPassword;
}

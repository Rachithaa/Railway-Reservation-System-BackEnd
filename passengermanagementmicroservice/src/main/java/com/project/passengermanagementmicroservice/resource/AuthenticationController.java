package com.project.passengermanagementmicroservice.resource;



import com.project.passengermanagementmicroservice.customexception.PassengerAlreadyExistException;
import com.project.passengermanagementmicroservice.model.AuthenticationRequest;
import com.project.passengermanagementmicroservice.model.AuthenticationResponse;
import com.project.passengermanagementmicroservice.model.Passenger;
import com.project.passengermanagementmicroservice.service.PassengerService;
import com.project.passengermanagementmicroservice.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/passenger/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {

    @Autowired
   private PassengerService passengerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
   private JwtUtils jwtUtils;


    @ApiOperation(value = "Register the passenger",notes = "All the details of passengers gets register",response = Passenger.class)
    @PostMapping("/signup")
    public ResponseEntity registerPassenger(@Valid @RequestBody Passenger passenger) throws PassengerAlreadyExistException
    {
        try {
            log.info("register the passenger");
            Passenger savePassenger =  passengerService.registerPassenger(passenger);
            ResponseEntity.ok(new AuthenticationResponse("Successfully registered"));
            return new ResponseEntity<>(savePassenger, HttpStatus.OK);
        }
        catch(PassengerAlreadyExistException passengerAlreadyExistException){
            log.error("passenger already exists");
            return new ResponseEntity("The passenger with "+passenger.getPassengerEmail()+" details already exist", HttpStatus.CONFLICT);
        }
        catch (Exception exception){
            log.error("passenger cannot register");
            return new ResponseEntity("Not able to register",HttpStatus.CONFLICT);
        }


    }


    @PostMapping("/signin")
    public ResponseEntity aunthenticateAdmin(@RequestBody AuthenticationRequest authenticationRequest)
    {
        //String username=authenticationRequest.getUserName();
        String email=authenticationRequest.getPassengerEmail();
        String password=authenticationRequest.getPassengerPassword();

        try {
           // System.out.println(email);
           // System.out.println(password);
            log.info("passenger signin");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        }
        catch (Exception e)
        {
            //return ResponseEntity.ok(new AuthenticationResponse("Not able to login"));
            log.error("passenger not able to signin");
            return new ResponseEntity("Error during authentication",HttpStatus.CONFLICT);
        }
        UserDetails loadpassenger=passengerService.loadUserByUsername(email);
        String generatedToken= jwtUtils.generateToken(loadpassenger);
       // System.out.println(generatedToken);
        //return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                generatedToken
        ).body(loadpassenger);
    }
}

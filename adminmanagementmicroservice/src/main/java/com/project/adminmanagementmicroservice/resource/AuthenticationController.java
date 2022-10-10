package com.project.adminmanagementmicroservice.resource;


import com.project.adminmanagementmicroservice.customexception.AdminAlreadyExistException;
import com.project.adminmanagementmicroservice.model.Admin;
import com.project.adminmanagementmicroservice.model.AuthenticationRequest;
import com.project.adminmanagementmicroservice.model.AuthenticationResponse;
import com.project.adminmanagementmicroservice.service.AdminService;
import com.project.adminmanagementmicroservice.utils.JwtUtils;
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
@RequestMapping("/admin/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {

    @Autowired
   private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
   private JwtUtils jwtUtils;

    @ApiOperation(value = "Register the admin",notes = "All the details of admin gets register",response = Admin.class)
    @PostMapping("/signup")
    public ResponseEntity registerAdmin(@Valid @RequestBody Admin admin) throws AdminAlreadyExistException
    {
        try {
            log.info("register admin");
            Admin saveAdmin =  adminService.registerAdmin(admin);
            ResponseEntity.ok(new AuthenticationResponse("Successfully registered"));
            return new ResponseEntity<>(saveAdmin, HttpStatus.OK);
        }
        catch(AdminAlreadyExistException adminAlreadyExistException){
            log.error("admin already exist");
            return new ResponseEntity("The admin with "+admin.getAdminEmail()+" details already exist", HttpStatus.CONFLICT);
        }
        catch (Exception exception){
            log.error("Not able to register");
            return ResponseEntity.ok(new AuthenticationResponse("Not able to register"));
        }

    }

    @PostMapping("/signin")
    public ResponseEntity aunthenticateAdmin(@RequestBody AuthenticationRequest authenticationRequest)
    {
        //String username=authenticationRequest.getUserName();
        String email=authenticationRequest.getAdminEmail();
        String password=authenticationRequest.getAdminPassword();

        try {
           // System.out.println(email);
           // System.out.println(password);
            log.info("admin login");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        }
        catch (Exception e)
        {
            log.error("admin not able login");
            //return ResponseEntity.ok(new AuthenticationResponse("Not able to login"));
            return new ResponseEntity("Error during authentication",HttpStatus.CONFLICT);
        }
        UserDetails loadAdmin=adminService.loadUserByUsername(email);
        String generatedToken= jwtUtils.generateToken(loadAdmin);
       // System.out.println(generatedToken);
      //  return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                generatedToken
                ).body(loadAdmin);
    }
}

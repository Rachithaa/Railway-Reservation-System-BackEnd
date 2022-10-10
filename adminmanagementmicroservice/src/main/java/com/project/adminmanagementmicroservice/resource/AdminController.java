package com.project.adminmanagementmicroservice.resource;

import com.project.adminmanagementmicroservice.customexception.AdminNotFoundException;
import com.project.adminmanagementmicroservice.model.Admin;
import com.project.adminmanagementmicroservice.model.Booking;
import com.project.adminmanagementmicroservice.model.Train;
import com.project.adminmanagementmicroservice.service.AdminService;
import com.project.adminmanagementmicroservice.service.TrainAndBookingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @Autowired
    AdminService adminService;

    @Autowired
    TrainAndBookingService trainAndBookingService;

    /*@ApiOperation(value = "Register the admin",notes = "All the details of admin gets register",response = Admin.class)
    @PostMapping("/adminregister")
    public ResponseEntity registerAdmin(@Valid @RequestBody Admin admin) throws AdminAlreadyExistException
    {
        try {
            Admin saveAdmin =  adminService.registerAdmin(admin);
            return new ResponseEntity<>(saveAdmin, HttpStatus.OK);
        }
        catch(AdminAlreadyExistException adminAlreadyExistException){
            return new ResponseEntity("The admin with "+admin.getAdminId()+" details already exist", HttpStatus.CONFLICT);
        }

    }*/

    @ApiOperation(value = "view admin by id",notes = "admin can view his details by id",response = Admin.class)
    @GetMapping("/viewadmin/{adminId}")
    public ResponseEntity viewAdmin(@PathVariable("adminId") String adminId)
    {
        try {
            log.info("view admin details");
            return new ResponseEntity(adminService.viewAdmin(adminId), HttpStatus.OK);
        }
        catch(AdminNotFoundException adminNotFoundException){
            log.error("cannot view admin details");
            return new ResponseEntity("The admin with admin id: "+adminId+" does not exists", HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "delete admin by id",notes = "super admin delete other admin by id",response = Admin.class)
    @DeleteMapping("/deleteadmin/{adminId}")
    public ResponseEntity deleteAdmin(@PathVariable("adminId") int adminId)
    {
        try {
            log.info("delete admin");
            var isdel = adminService.deleteAdmin(adminId);
            if (isdel) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        catch(AdminNotFoundException adminNotFoundException){
            log.error("cannot delete the admin");
            return new ResponseEntity("The admin with admin id: "+adminId+" does not exists", HttpStatus.CONFLICT);
        }
        return null;
    }

    @ApiOperation(value = "update admin by id",notes = "update the required details of admin by id",response = Admin.class)
    @PutMapping("/updateadmin/{adminId}")
    public ResponseEntity updateAdmin(@PathVariable("adminId") int id,@Valid @RequestBody Admin admin)
    {
        try
        {
            log.info("update the admin");
            return  new ResponseEntity(adminService.updateAdmin(id,admin),HttpStatus.OK);
        }
        catch(AdminNotFoundException adminNotFoundException){
            log.error("cannot update the train");
            return new ResponseEntity("The admin with admin id: "+id+" does not exists", HttpStatus.CONFLICT);
        }
    }
    @ApiOperation(value = "add train",notes = "admin adds all the required details of train",response = Train.class)
    @PostMapping("/adminaddtrain")
    public ResponseEntity addTrain(@Valid @RequestBody Train train) {
        log.info("admin add train");
        return trainAndBookingService.addTrain(train);

    }

    @ApiOperation(value = "get all trains",notes = "admin can get all train details",response = Train.class)
    @GetMapping("/adminlistalltrain")
    public ResponseEntity listAllTrain()
    {
        log.info("admin list all train");
      return trainAndBookingService.listAllTrain();
    }

    @ApiOperation(value = "get train details by id",notes = "admin can get specified train and its details",response = Train.class)
    @GetMapping("/adminlisttrain/{trainId}")
    public ResponseEntity listTrain(@PathVariable("trainId") int trainId )
    {
        log.info("admin list particular train");
        return trainAndBookingService.listTrain(trainId);
    }

    @ApiOperation(value = "update train by id",notes = "admin update the specified train details",response = Train.class)
    @PutMapping("/adminupdatetrain/{trainId}")
    public ResponseEntity updateTrain(@PathVariable("trainId") int trainId,@Valid @RequestBody Train train)
    {
        log.info("admin update train");
        return trainAndBookingService.updateTrain(trainId,train);
    }

    @ApiOperation(value = "delete train by id",notes = "admin can delete the specified train details",response = Train.class)
    @DeleteMapping("/admindeletetrain/{trainId}")
    public ResponseEntity deleteTrain(@PathVariable("trainId") int trainId)
    {
        log.info("admin delete train");
        return trainAndBookingService.deleteTrain(trainId);
    }
    @ApiOperation(value = "admin get the to the passengers from train number(train id)",notes = "admin get the to know the passengers who booked from train number(train id)",response = Booking.class)
    @GetMapping("/adminbookingdetails/{trainId}")
    public ResponseEntity getBookingDetails(@PathVariable("trainId") int trainId)
    {
        log.info("admin get booking details");
        return  trainAndBookingService.getBookingDetails(trainId);

    }
}

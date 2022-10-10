package com.project.trainmanagementmicroservice.resource;

import com.project.trainmanagementmicroservice.customexception.JourneyDateInvalidException;
import com.project.trainmanagementmicroservice.customexception.TrainAlreadyExistException;
import com.project.trainmanagementmicroservice.customexception.TrainNotFoundException;
import com.project.trainmanagementmicroservice.model.Train;
import com.project.trainmanagementmicroservice.service.TrainService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trains")
@Slf4j
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/addTrain")
    @ApiOperation(value = "Add Train",notes = "Adding the train and its details",response = Train.class)
    public ResponseEntity addTrain(@Valid @RequestBody Train train) throws TrainAlreadyExistException
    {
        try {
            log.info(" add train");
            Train savedTrain = trainService.addTrain(train);
            return new ResponseEntity<>(savedTrain, HttpStatus.OK);
        }
        catch(TrainAlreadyExistException trainAlreadyExistException){
            log.error("cannot add train");
            return new ResponseEntity("The train with "+train.getTrainId()+" details already exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/alllist")
    @ApiOperation(value = "view trains",notes = "gets all train details",response = Train.class)
    public ResponseEntity<List>  listAllTrain() throws TrainNotFoundException
    {
        try {
            log.info("list of all train");
            return new ResponseEntity<List>((List) trainService.listAllTrain(), HttpStatus.OK);
        }
        catch(TrainNotFoundException trainNotFoundException){
            log.error("cannot get list of all train");
            return new ResponseEntity("The trains details does not exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/listTrain/{trainId}")
    @ApiOperation(value = "view by id",notes = "gives the details of a particular train by id",response = Train.class)
    public ResponseEntity listTrain(@PathVariable("trainId") int trainId) throws TrainNotFoundException
    {
        try {
            log.info("view a particular train");
            return new ResponseEntity(trainService.listTrain(trainId), HttpStatus.OK);
        }
        catch(TrainNotFoundException trainNotFoundException){
            log.error("cannot view a particular train");
            return new ResponseEntity("The train with train number "+trainId+" does not exists", HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/update/{trainId}")
    @ApiOperation(value = "update by id",notes="updates a particular train details based on the id",response = Train.class)
    public ResponseEntity updateTrain(@PathVariable("trainId") int trainId,@Valid @RequestBody Train train) throws TrainNotFoundException{

        try
        {
            log.info("update a particular train");
            return  new ResponseEntity(trainService.updateTrain(trainId,train),HttpStatus.OK);
        }
        catch(TrainNotFoundException trainNotFoundException){
            log.error("cannot update the train");
            return new ResponseEntity("The train with train number"+trainId+" does not exists", HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/delete/{trainId}")
    @ApiOperation(value="delete by id",notes="delete the train by its id",response = Train.class)
    public ResponseEntity deleteTrain(@PathVariable("trainId") int trainId) throws TrainNotFoundException
    {
        try {
            log.info("delete a train");
            var isdel = trainService.deleteTrain(trainId);
            if (isdel) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        catch(TrainNotFoundException trainNotFoundException){
            log.error("cannot delete the train");
            return new ResponseEntity("The train with train number"+trainId+" does not exists", HttpStatus.CONFLICT);
        }

        return null;
    }

    //to do search operation
    @GetMapping("/search/{source}/{destination}/{journeydate}")
    @ApiOperation(value = "search by source,destination,journey date",notes="search a particular train details based on the source,destination,journey date",response = Train.class)
    public ResponseEntity searchTrain(@PathVariable("source") String source,@PathVariable("destination") String destination,@PathVariable("journeydate") String journeydate )
    {
        try
        {
            log.info("search a train on basis of source,destination, journey date");
            return  new ResponseEntity(trainService.searchTrain(source,destination,journeydate),HttpStatus.OK);
        }
        catch (JourneyDateInvalidException journeyDateInvalidException)
        {
            log.error("cannot search the train due to invalid journey date");
            return new ResponseEntity("The train does not exists on this particular date", HttpStatus.CONFLICT);
        }
        catch(TrainNotFoundException trainNotFoundException){
            log.error("cannot search the train due to train doesnot exist");
            return new ResponseEntity("The train does not exists", HttpStatus.CONFLICT);
        } catch (ParseException e) {
            log.error("cannot search the train");
            throw new RuntimeException(e);
        }
    }
}

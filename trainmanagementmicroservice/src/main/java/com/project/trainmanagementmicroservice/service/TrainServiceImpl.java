package com.project.trainmanagementmicroservice.service;

import com.project.trainmanagementmicroservice.customexception.JourneyDateInvalidException;
import com.project.trainmanagementmicroservice.customexception.TrainAlreadyExistException;
import com.project.trainmanagementmicroservice.customexception.TrainNotFoundException;
import com.project.trainmanagementmicroservice.model.Train;
import com.project.trainmanagementmicroservice.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService{

    @Autowired
    TrainRepository trainRepository;

    @Override
    public Train addTrain(Train train) {
        if(trainRepository.existsById(train.getTrainId()))
        {
            throw new TrainAlreadyExistException();
        }
        return trainRepository.save(train);
    }

    @Override
    public List<Train> listAllTrain() {
        if(trainRepository.findAll().isEmpty())
        {
            throw new TrainNotFoundException();
        }
        return trainRepository.findAll();
    }

    @Override
    public Optional<Train> listTrain(int id) {
        if(trainRepository.findById(id).isEmpty())
        {
            throw new TrainNotFoundException();

        }
        return trainRepository.findById(id);
    }

    @Override
    public Train updateTrain(int id,Train train) {
        Optional<Train> train1=trainRepository.findById(id);
        if(train1.isPresent())
        {
            Train train2=train1.get();
            if(train.getTrainName()!=null && !train.getTrainName().isEmpty())
                train2.setTrainName(train.getTrainName());
            if(train.getSource()!=null && !train.getSource().isEmpty())
                train2.setSource(train.getSource());
            if(train.getDestination()!=null && !train.getDestination().isEmpty())
                train2.setDestination(train.getDestination());
            if(train.getPricePerKms()!=0)
                train2.setPricePerKms(train.getPricePerKms());
            if(train.getDaysOfRunning()!=null)
                train2.setDaysOfRunning(train.getDaysOfRunning());
            if(train.getTotalNumOfSeats()!=0)
                train2.setTotalNumOfSeats(train.getTotalNumOfSeats());
            if(train.getTrainClasses()!=null)
                train2.setTrainClasses(train.getTrainClasses());
            if(train.getRoute()!=null)
                train2.setRoute(train.getRoute());
            return trainRepository.save(train2);
        }
        else {
            throw new TrainNotFoundException();
        }
    }

    @Override
    public boolean deleteTrain(int id) {
        if(trainRepository.findById(id).isEmpty())
        {
            throw new TrainNotFoundException();
        }
        trainRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Train> searchTrain(String source, String destination,String journeydate) throws ParseException {

        //Date today = new Date();
        List<Train> train;
        String[] daysofweek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date jdt = df.parse(journeydate);
        String today = df.format(new Date());
        Date jdt1 = df.parse(today);
        //String jdt1= df.format(today);
        //System.out.println(jdt1);
        //System.out.println(jdt);
        if (jdt.before(jdt1)) {
            throw new JourneyDateInvalidException();
        }
        else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(jdt);
            int dw = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("Day of week " + (dw - 1));
            System.out.println(daysofweek[dw - 1]);
            System.out.println("source " + source);
            System.out.println("destination " + destination);
            train = trainRepository.search(source, destination, daysofweek[dw - 1]);
            if (train.isEmpty()) {
                throw new TrainNotFoundException();
            }
            else {
                return train;
            }
        }
    }


}

package com.project.passengermanagementmicroservice.service;

import com.project.passengermanagementmicroservice.customexception.PassengerAlreadyExistException;
import com.project.passengermanagementmicroservice.customexception.PassengerNotFoundException;
import com.project.passengermanagementmicroservice.model.Passenger;
import com.project.passengermanagementmicroservice.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService{

   @Autowired
   private PassengerRepository passengerRepository;

   @Autowired
   private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Passenger registerPassenger(Passenger passenger) {
        if(passengerRepository.existsByEmail(passenger.getPassengerEmail())!=null)
        {
            throw new PassengerAlreadyExistException();
        }
        passenger.setPassengerId(sequenceGeneratorService.getSequenceNumber(Passenger.SEQUENCE_NAME));
        return passengerRepository.save(passenger);
    }

    @Override
    public Optional<Passenger> viewPassenger(String email){
        if(passengerRepository.findByEmail(email)==null)
        {
            throw  new PassengerNotFoundException();
        }
        return Optional.ofNullable(passengerRepository.findByEmail(email));
    }

    @Override
    public boolean deletePassenger(int id) {
        if(passengerRepository.findById(id).isEmpty())
        {
            throw new PassengerNotFoundException();
        }
        passengerRepository.deleteById(id);
        return true;
    }

    @Override
    public Passenger updatePassenger(int id, Passenger passenger) {
        Optional<Passenger>  passenger1=passengerRepository.findById(id);
        if(passenger1.isPresent())
        {
            Passenger passenger2=passenger1.get();
            if(passenger.getPassengerName()!=null && !passenger.getPassengerName().isEmpty())
                passenger2.setPassengerName(passenger.getPassengerName());
            if(passenger.getPassengerPassword()!=null && !passenger.getPassengerPassword().isEmpty())
                passenger2.setPassengerPassword(passenger.getPassengerPassword());
            if(passenger.getPassengerEmail()!=null && !passenger.getPassengerEmail().isEmpty())
                passenger2.setPassengerEmail(passenger.getPassengerEmail());
            if(passenger.getPassengerPhone()!=null && !passenger.getPassengerPhone().isEmpty())
                passenger2.setPassengerPhone(passenger.getPassengerPhone());
            if(passenger.getPassengerAddress()!=null && !passenger.getPassengerAddress().isEmpty())
                passenger2.setPassengerAddress(passenger.getPassengerAddress());
            return passengerRepository.save(passenger2);
        }
        else {
            throw new PassengerNotFoundException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Passenger passenger=passengerRepository.findByEmail(email);
        if(passenger==null)
            return null;

        String email2=passenger.getPassengerEmail();
        //String username2=admin.getUserName();
        String pwd=passenger.getPassengerPassword();

        return new User(email2,pwd,new ArrayList<>());
    }
}

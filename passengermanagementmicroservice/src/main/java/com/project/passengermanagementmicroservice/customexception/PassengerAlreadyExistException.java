package com.project.passengermanagementmicroservice.customexception;


public class PassengerAlreadyExistException extends RuntimeException {

    private String msg;

    public PassengerAlreadyExistException()
    {

    }

    public PassengerAlreadyExistException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

}

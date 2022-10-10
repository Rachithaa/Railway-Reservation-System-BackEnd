package com.project.passengermanagementmicroservice.customexception;

public class PassengerNotFoundException extends RuntimeException{

    private String msg;

    public PassengerNotFoundException()
    {

    }

    public PassengerNotFoundException(String msg)
    {
        super(msg);
        this.msg=msg;
    }
}

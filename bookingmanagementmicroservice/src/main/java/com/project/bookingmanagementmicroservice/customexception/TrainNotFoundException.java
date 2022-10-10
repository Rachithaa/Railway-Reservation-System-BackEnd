package com.project.bookingmanagementmicroservice.customexception;

public class TrainNotFoundException extends RuntimeException{

    private String msg;

    public TrainNotFoundException()
    {

    }

    public TrainNotFoundException(String msg)
    {
        super(msg);
        this.msg=msg;
    }
}

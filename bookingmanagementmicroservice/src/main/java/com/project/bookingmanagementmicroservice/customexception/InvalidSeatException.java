package com.project.bookingmanagementmicroservice.customexception;

public class InvalidSeatException extends RuntimeException{

    private String msg;

    public InvalidSeatException()
    {

    }

    public InvalidSeatException(String msg)
    {
        super(msg);
        this.msg=msg;
    }
}

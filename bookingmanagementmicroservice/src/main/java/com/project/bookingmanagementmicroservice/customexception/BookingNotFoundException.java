package com.project.bookingmanagementmicroservice.customexception;


public class BookingNotFoundException extends RuntimeException {

    private String msg;

    public BookingNotFoundException()
    {

    }

    public BookingNotFoundException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

}

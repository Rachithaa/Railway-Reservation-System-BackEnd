package com.project.trainmanagementmicroservice.customexception;


public class JourneyDateInvalidException extends RuntimeException {

    private String msg;

    public JourneyDateInvalidException()
    {

    }

    public JourneyDateInvalidException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

}

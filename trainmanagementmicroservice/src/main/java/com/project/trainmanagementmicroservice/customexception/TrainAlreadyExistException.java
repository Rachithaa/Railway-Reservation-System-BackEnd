package com.project.trainmanagementmicroservice.customexception;


public class TrainAlreadyExistException extends RuntimeException {

    private String msg;

    public TrainAlreadyExistException()
    {

    }

    public TrainAlreadyExistException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

}

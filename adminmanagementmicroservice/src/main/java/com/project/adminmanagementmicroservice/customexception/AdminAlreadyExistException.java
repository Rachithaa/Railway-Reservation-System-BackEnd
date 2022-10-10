package com.project.adminmanagementmicroservice.customexception;


public class AdminAlreadyExistException extends RuntimeException {

    private String msg;

    public AdminAlreadyExistException()
    {

    }

    public AdminAlreadyExistException(String msg)
    {
        super(msg);
        this.msg=msg;
    }

}

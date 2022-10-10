package com.project.adminmanagementmicroservice.customexception;

public class AdminNotFoundException extends RuntimeException{

    private String msg;

    public AdminNotFoundException()
    {

    }

    public AdminNotFoundException(String msg)
    {
        super(msg);
        this.msg=msg;
    }
}

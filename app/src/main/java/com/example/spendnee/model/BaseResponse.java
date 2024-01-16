package com.example.spendnee.model;

public class BaseResponse {

    private String isSuccess;
    private String message;
    private User user;

    public String getIsSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public User getUser(){
        return user;
    }

}

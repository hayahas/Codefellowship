package com.example.LTUC.Codefellowship.exceptions;

public class UserNotFoundExceptions extends RuntimeException{

    public UserNotFoundExceptions(String message){
        super(message);
    }

    public UserNotFoundExceptions(Throwable cause){
        super(cause);
    }

}

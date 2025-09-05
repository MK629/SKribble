package com.sKribble.api.error.exceptions.CRUDExceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 111L;

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

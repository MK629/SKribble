package com.sKribble.api.error.exceptions.CRUDExceptions;

public class IllegalInputException extends RuntimeException{

    private static final long serialVersionUID = 109L;

    public IllegalInputException(String message){
        super(message);
    }

    public IllegalInputException(String message, Throwable cause){
        super(message, cause);
    }
}

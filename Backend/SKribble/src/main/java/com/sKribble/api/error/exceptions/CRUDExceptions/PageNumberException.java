package com.sKribble.api.error.exceptions.CRUDExceptions;

public class PageNumberException extends RuntimeException{

    private static final long serialVersionUID = 112L;

    public PageNumberException(String message){
        super(message);
    }

    public PageNumberException(String message, Throwable cause){
        super(message, cause);
    }
}

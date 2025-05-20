package com.sKribble.api.error.exceptions.CRUDExceptions;

public class ContentNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 108L;

    public ContentNotFoundException(String message){
        super(message);
    }

    public ContentNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

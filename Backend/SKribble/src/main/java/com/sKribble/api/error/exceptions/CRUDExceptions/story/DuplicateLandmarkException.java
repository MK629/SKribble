package com.sKribble.api.error.exceptions.CRUDExceptions.story;

public class DuplicateLandmarkException extends RuntimeException{

    private static final long serialVersionUID = 110L;

    public DuplicateLandmarkException(String message){
        super(message);
    }

    public DuplicateLandmarkException(String message, Throwable cause){
        super(message, cause);
    }
}

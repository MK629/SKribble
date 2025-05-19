package com.sKribble.api.error.exceptions.CRUDExceptions;

public class ProjectNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 107L;

    public ProjectNotFoundException(String message){
        super(message);
    }

    public ProjectNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

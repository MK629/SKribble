package com.sKribble.api.error.exceptions.CRUDExceptions;

public class PersistenceErrorException extends RuntimeException{
    
    private static final long serialVersionUID = 106L;

    public PersistenceErrorException(String message){
        super(message);
    }

    public PersistenceErrorException(String message, Throwable cause){
        super(message, cause);
    }
}

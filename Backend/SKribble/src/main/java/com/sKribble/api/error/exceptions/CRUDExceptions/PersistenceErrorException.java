package com.sKribble.api.error.exceptions.CRUDExceptions;

public class PersistenceErrorException extends RuntimeException{
    
    public static final long serialVersionUID = 106L;

    public PersistenceErrorException(String message){
        super(message);
    }

    public PersistenceErrorException(String message, Throwable e){
        super(message, e);
    }
}

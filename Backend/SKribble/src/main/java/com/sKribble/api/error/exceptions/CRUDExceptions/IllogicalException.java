package com.sKribble.api.error.exceptions.CRUDExceptions;

public class IllogicalException extends RuntimeException{

    public static final long serialVersionUID = 103L;

    public IllogicalException(String message){
        super(message);
    }

    public IllogicalException(String message, Throwable cause){
        super(message, cause);
    }
}

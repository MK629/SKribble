package com.sKribble.api.error.exceptions.CRUDExceptions;

public class IllogicalNullException extends RuntimeException{

    public static final long serialVersionUID = 103L;

    public IllogicalNullException(String message){
        super(message);
    }

    public IllogicalNullException(String message, Throwable cause){
        super(message, cause);
    }
}

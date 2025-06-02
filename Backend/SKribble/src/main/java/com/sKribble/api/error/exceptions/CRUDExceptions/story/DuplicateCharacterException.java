package com.sKribble.api.error.exceptions.CRUDExceptions.story;

public class DuplicateCharacterException extends RuntimeException{

    private static final long serialVersionUID = 104L;

    public DuplicateCharacterException(String message){
        super(message);
    }

    public DuplicateCharacterException(String message, Throwable cause){
        super(message, cause);
    }
}

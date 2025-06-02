package com.sKribble.api.error.exceptions.CRUDExceptions.story;

public class DuplicateChapterException extends RuntimeException{

    private static final long serialVersionUID = 102L;

    public DuplicateChapterException(String message){
        super(message);
    }

    public DuplicateChapterException(String message, Throwable cause){
        super(message, cause);
    }
}

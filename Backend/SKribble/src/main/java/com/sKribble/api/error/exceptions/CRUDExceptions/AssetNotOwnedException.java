package com.sKribble.api.error.exceptions.CRUDExceptions;

import lombok.Getter;

@Getter
public class AssetNotOwnedException extends RuntimeException{

    private static final long serialVersionUID = 105L;

    private String responseMessage;

    private String consoleMessage;

    public AssetNotOwnedException(String message){
        super(message);
    }

    public AssetNotOwnedException(String message, Throwable e){
        super(message, e);
    }
}

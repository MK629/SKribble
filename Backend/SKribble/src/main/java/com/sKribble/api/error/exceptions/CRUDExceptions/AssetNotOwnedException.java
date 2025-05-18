package com.sKribble.api.error.exceptions.CRUDExceptions;

public class AssetNotOwnedException extends RuntimeException{

    public static final long serialVersionUID = 105L;

    public AssetNotOwnedException(String message){
        super(message);
    }

    public AssetNotOwnedException(String message, Throwable e){
        super(message, e);
    }
}

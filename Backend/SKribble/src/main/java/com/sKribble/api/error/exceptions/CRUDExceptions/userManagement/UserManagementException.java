package com.sKribble.api.error.exceptions.CRUDExceptions.userManagement;

public class UserManagementException extends RuntimeException{

    private static final long serialVersionUID = 109L;

    public UserManagementException(String message){
        super(message);
    }

    public UserManagementException(String message, Throwable cause){
        super(message, cause);
    }
}

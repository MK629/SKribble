package com.sKribble.api.error.exceptions.CRUDExceptions;

public class UserRegstrationErrorException extends RuntimeException{

	private static final long serialVersionUID = 101L;

	public UserRegstrationErrorException(String message) {
		super(message);
	}

	public UserRegstrationErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}

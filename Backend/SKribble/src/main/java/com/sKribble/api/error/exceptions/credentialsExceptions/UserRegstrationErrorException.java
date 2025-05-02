package com.sKribble.api.error.exceptions.credentialsExceptions;

public class UserRegstrationErrorException extends RuntimeException{

	private static final long serialVersionUID = 1180438378911560594L;

	public UserRegstrationErrorException(String message) {
		super(message);
	}

	public UserRegstrationErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}

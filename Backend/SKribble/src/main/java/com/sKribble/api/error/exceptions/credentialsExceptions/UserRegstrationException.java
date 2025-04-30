package com.sKribble.api.error.exceptions.credentialsExceptions;

public class UserRegstrationException extends RuntimeException{

	private static final long serialVersionUID = 1180438378911560594L;

	public UserRegstrationException(String message) {
		super(message);
	}

	public UserRegstrationException(String message, Throwable cause) {
		super(message, cause);
	}
}

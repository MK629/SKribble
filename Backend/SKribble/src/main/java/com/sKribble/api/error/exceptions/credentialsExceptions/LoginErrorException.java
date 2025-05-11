package com.sKribble.api.error.exceptions.credentialsExceptions;

public class LoginErrorException extends RuntimeException{

	private static final long serialVersionUID = 201L;

	public LoginErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginErrorException(String message) {
		super(message);
	}
}

package com.sKribble.api.error.exceptions.credentialsExceptions;

public class LoginErrorException extends RuntimeException{

	private static final long serialVersionUID = 6949337144876635424L;

	public LoginErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}

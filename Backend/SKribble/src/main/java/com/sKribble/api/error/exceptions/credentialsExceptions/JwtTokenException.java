package com.sKribble.api.error.exceptions.credentialsExceptions;

public class JwtTokenException extends RuntimeException{

	private static final long serialVersionUID = 202L;

	public JwtTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public JwtTokenException(String message) {
		super(message);

	}
}

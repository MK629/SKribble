package com.sKribble.api.error.exceptions.credentialsExceptions;

public class JwtTokenException extends RuntimeException{

	public JwtTokenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public JwtTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -4928789242581318652L;

}

package com.sKribble.api.error.exceptions.enumExceptions;

public class UnknownEnumException extends RuntimeException{

	private static final long serialVersionUID = 301L;

	public UnknownEnumException(String message) {
		super(message);
	}
	
	public UnknownEnumException(String message, Throwable cause) {
		super(message, cause);
	}
}

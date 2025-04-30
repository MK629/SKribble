package com.sKribble.api.error.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sKribble.api.controller.ApplicationServiceController;
import com.sKribble.api.error.exceptions.enumExceptions.UnknownEnumException;

@ControllerAdvice(assignableTypes = ApplicationServiceController.class)
public class ApplicationServiceExceptionHandler {

	@ExceptionHandler(UnknownEnumException.class)
	public ResponseEntity<String> handleEnumException(UnknownEnumException e){
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}

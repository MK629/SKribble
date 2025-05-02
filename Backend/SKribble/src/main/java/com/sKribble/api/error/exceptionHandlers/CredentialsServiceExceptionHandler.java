package com.sKribble.api.error.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sKribble.api.controller.CredentialsServiceController;
import com.sKribble.api.error.exceptions.credentialsExceptions.UserRegstrationErrorException;
import com.sKribble.api.error.exceptions.enumExceptions.UnknownEnumException;
import com.sKribble.api.utils.ResponseEntityUtil;

@RestControllerAdvice(assignableTypes =  CredentialsServiceController.class)
public class CredentialsServiceExceptionHandler {

	@ExceptionHandler(UnknownEnumException.class)
	public ResponseEntity<String> handleUnknownEnumException(UnknownEnumException e){
		return ResponseEntityUtil.return400(e);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleInputExceptions(MethodArgumentNotValidException e){
		return ResponseEntityUtil.return400(new RuntimeException(e.getBindingResult().getFieldError().getDefaultMessage()));
	}
	
	@ExceptionHandler(UserRegstrationErrorException.class)
	public ResponseEntity<String> handleUserRegstrationException(UserRegstrationErrorException e){
		return ResponseEntityUtil.return400(e);
	}
}

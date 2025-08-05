package com.sKribble.api.error.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sKribble.api.controller.CredentialsServiceController;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserRegstrationErrorException;
import com.sKribble.api.error.exceptions.credentialsExceptions.LoginErrorException;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.utils.ResponseEntityUtil;

@RestControllerAdvice(assignableTypes = CredentialsServiceController.class)
public class CredentialsServiceExceptionHandler {
	
	@SuppressWarnings("null")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleInputExceptions(MethodArgumentNotValidException e){
		return ResponseEntityUtil.return400(new RuntimeException(e.getBindingResult().getFieldError() != null ? e.getBindingResult().getFieldError().getDefaultMessage() : InputErrorMessages.INVALID_INPUT));
	}
	
	@ExceptionHandler(UserRegstrationErrorException.class)
	public ResponseEntity<String> handleUserRegstrationException(UserRegstrationErrorException e){
		return ResponseEntityUtil.return400(e);
	}
	
	@ExceptionHandler(LoginErrorException.class)
	public ResponseEntity<String> handleLoginErrorException(LoginErrorException e){
		return ResponseEntityUtil.return401(e);
	}

	@ExceptionHandler(PersistenceErrorException.class)
	public ResponseEntity<String> handlePersistenceErrorException(PersistenceErrorException e){
		return ResponseEntityUtil.return500(e);
	}
}

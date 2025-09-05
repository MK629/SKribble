package com.sKribble.api.error.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sKribble.api.controller.SKribbleCommonServiceController;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.error.exceptions.CRUDExceptions.ProjectNotFoundException;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserNotFoundException;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.utils.ResponseEntityUtil;

@RestControllerAdvice(assignableTypes = SKribbleCommonServiceController.class)
public class SKribbleCommonServiceExceptionHandler {

	@SuppressWarnings("null")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleInputExceptions(MethodArgumentNotValidException e){
		return ResponseEntityUtil.return400(new RuntimeException(e.getBindingResult().getFieldError() != null ? e.getBindingResult().getFieldError().getDefaultMessage() : InputErrorMessages.INVALID_INPUT));
	}

	@ExceptionHandler(AssetNotOwnedException.class)
	public ResponseEntity<String> handleAssetNotOwnedException(AssetNotOwnedException e){
		return ResponseEntityUtil.return403(e);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
		return ResponseEntityUtil.return404(e);
	}

	@ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException e){
		return ResponseEntityUtil.return404(e);
	}

	@ExceptionHandler(IllogicalNullException.class)
	public ResponseEntity<String> handleIllogicalNullException(IllogicalNullException e){
		return ResponseEntityUtil.return500(e);
	}

    @ExceptionHandler(PersistenceErrorException.class)
	public ResponseEntity<String> handlePersistenceErrorException(PersistenceErrorException e){
		return ResponseEntityUtil.return500(e);
	}
}

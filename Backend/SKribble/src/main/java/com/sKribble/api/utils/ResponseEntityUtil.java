package com.sKribble.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {
	
	private static final String errorCausePrefix = " Root cause: ";
	
	
	//Success responses
	public static ResponseEntity<String> return200(String message){
		return buildSuccessResponseEntity(message, HttpStatus.OK);
	}
	
	public static ResponseEntity<String> return201(String message){
		return buildSuccessResponseEntity(message, HttpStatus.CREATED);
	}
	
	//Error responses
	public static ResponseEntity<String> return400(Exception e){
		return buildErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<String> return401(Exception e){
		return buildErrorResponseEntity(e, HttpStatus.UNAUTHORIZED);
	}
	
	public static ResponseEntity<String> return403(Exception e){
		return buildErrorResponseEntity(e, HttpStatus.FORBIDDEN);
	}
	
	public static ResponseEntity<String> return404(Exception e){
		return buildErrorResponseEntity(e, HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<String> return500(Exception e){
		return buildErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//Centralized functions
	
	//Success
	private static ResponseEntity<String> buildSuccessResponseEntity(String message, HttpStatus httpStatus){
		return new ResponseEntity<String>(message, httpStatus);
	}
	
	//Error
	private static ResponseEntity<String> buildErrorResponseEntity(Exception e, HttpStatus httpStatus) {
		
		String cause = "";
		
		if(e.getCause() != null) {
			cause = errorCausePrefix + e.getCause().getMessage();
		}

		return new ResponseEntity<String>(e.getMessage() + cause, httpStatus);
	}
}

package com.sKribble.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sKribble.api.dto.output.user.TokenCarrier;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResponseEntityUtil {
	
	private static final String errorCausePrefix = " Root cause: ";
	
	//Success responses
	public static ResponseEntity<String> return200(String message){
		return buildSuccessResponseEntity(message, HttpStatus.OK);
	}
	
	public static ResponseEntity<String> return201(String message){
		return buildSuccessResponseEntity(message, HttpStatus.CREATED);
	}

	//Response with token
	public static ResponseEntity<TokenCarrier> returnToken(TokenCarrier tokenCarrier){
		return new ResponseEntity<TokenCarrier>(tokenCarrier, HttpStatus.OK);
	}
	
	//Error responses
	public static ResponseEntity<String> return400(Throwable e){
		return buildErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<String> return401(Throwable e){
		return buildErrorResponseEntity(e, HttpStatus.UNAUTHORIZED);
	}
	
	public static ResponseEntity<String> return403(Throwable e){
		return buildErrorResponseEntity(e, HttpStatus.FORBIDDEN);
	}
	
	public static ResponseEntity<String> return404(Throwable e){
		return buildErrorResponseEntity(e, HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<String> return500(Throwable e){
		return buildErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
//==========================================[ Here lies the line for local abstractions ]=====================================================//
	
	//Success
	private static ResponseEntity<String> buildSuccessResponseEntity(String message, HttpStatus httpStatus){
		return new ResponseEntity<String>(message, httpStatus);
	}
	
	//Error
	private static ResponseEntity<String> buildErrorResponseEntity(Throwable e, HttpStatus httpStatus) {
		String cause = "";
		
		if(e.getCause() != null) {
			cause += e.getCause().getMessage();
		}

		//Log to console by default if status is 401, 403 or 500.
		if(httpStatus == HttpStatus.UNAUTHORIZED || httpStatus == HttpStatus.FORBIDDEN || httpStatus == HttpStatus.INTERNAL_SERVER_ERROR){
			String causeLog = (cause.isEmpty() || cause.isBlank()) ? "" : errorCausePrefix + cause;
			log.error(e.getMessage() + causeLog);
		}

		return new ResponseEntity<String>(e.getMessage(), httpStatus);
	}
}

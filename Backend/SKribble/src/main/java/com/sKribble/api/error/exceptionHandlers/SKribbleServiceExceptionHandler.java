package com.sKribble.api.error.exceptionHandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.sKribble.api.controller.SKribbleServiceController;

@ControllerAdvice(assignableTypes = SKribbleServiceController.class)
public class SKribbleServiceExceptionHandler {
	
}

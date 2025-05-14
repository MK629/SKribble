package com.sKribble.api.error.exceptionHandlers;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sKribble.api.controller.SKribbleServiceController;
import com.sKribble.api.utils.GraphQlErrorUtil;

import graphql.GraphQLError;

@ControllerAdvice(assignableTypes = SKribbleServiceController.class)
public class SKribbleServiceExceptionHandler {
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GraphQLError handleInputExceptions(MethodArgumentNotValidException e){
        return GraphQlErrorUtil.return400(new Exception(e.getBindingResult().getFieldError().getDefaultMessage()));
    }
}

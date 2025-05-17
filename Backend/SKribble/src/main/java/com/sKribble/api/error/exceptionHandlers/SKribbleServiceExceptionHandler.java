package com.sKribble.api.error.exceptionHandlers;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.sKribble.api.utils.GraphQlErrorUtil;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;

//Can't use @ControllerAdvice for graphQl related stuff.
@Component
public class SKribbleServiceExceptionHandler extends DataFetcherExceptionResolverAdapter{

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof ConstraintViolationException){
            return GraphQlErrorUtil.return400(ex);
        }
        
        return GraphQlErrorUtil.return500(ex);
    }

}

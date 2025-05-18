package com.sKribble.api.error.exceptionHandlers;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateChapterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateCharacterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.utils.GraphQlErrorUtil;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;

//Can't use @ControllerAdvice for graphQl related stuff.
@Component //Define it as a bean. (Fucking hell)
public class SKribbleServiceExceptionHandler extends DataFetcherExceptionResolverAdapter{

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof ConstraintViolationException || ex instanceof DuplicateChapterException || ex instanceof DuplicateCharacterException){
            return GraphQlErrorUtil.return400(ex);
        }

        else if(ex instanceof AssetNotOwnedException){
            return GraphQlErrorUtil.return403(ex);
        }
        
        else if(ex instanceof IllogicalNullException || ex instanceof PersistenceErrorException){
            return GraphQlErrorUtil.return500(ex);
        }
        
        return GraphQlErrorUtil.return500(ex);
    }
}

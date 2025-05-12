package com.sKribble.api.utils;

import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GraphQlErrorUtil {

    private static final String errorCausePrefix = " Root cause:";

    public static GraphQLError return400(Exception e){
        return buildGraphQLError(e, ErrorType.BAD_REQUEST);
    }

    public static GraphQLError return401(Exception e){
        return buildGraphQLError(e, ErrorType.UNAUTHORIZED);
    }
    
    public static GraphQLError return403(Exception e){
        return buildGraphQLError(e, ErrorType.FORBIDDEN);
    }

    public GraphQLError return404(Exception e){
        return buildGraphQLError(e, ErrorType.NOT_FOUND);
    }

    public GraphQLError return500(Exception e){
        return buildGraphQLError(e, ErrorType.INTERNAL_ERROR);
    }

    private static GraphQLError buildGraphQLError(Exception e, ErrorType errorType){
        
        String cause = " ";

        if(e.getCause() != null){
            cause += e.getCause().getMessage();
        }
        else{
            cause = "";
        }

        if(errorType == ErrorType.UNAUTHORIZED || errorType == ErrorType.FORBIDDEN || errorType == ErrorType.INTERNAL_ERROR){
            String causeLog = (cause.isEmpty() || cause.isBlank()) ? "" : errorCausePrefix + cause;
            log.error(e.getMessage() + causeLog);
        }

        return GraphqlErrorBuilder.newError().message(e.getMessage() + cause).errorType(errorType).build();
    }

}

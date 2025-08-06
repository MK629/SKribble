package com.sKribble.api.utils;

import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.log4j.Log4j2;

/**
 * <h4>A util class that builds various GraphQL error responses that contains messages from thrown exceptions.</h4>
 */
@Log4j2
public class GraphQlErrorUtil {

    private static final String errorCausePrefix = " Root cause: ";

    public static GraphQLError return400(Throwable e){
        return buildGraphQLError(e, ErrorType.BAD_REQUEST);
    }

    public static GraphQLError return401(Throwable e){
        return buildGraphQLError(e, ErrorType.UNAUTHORIZED);
    }
    
    public static GraphQLError return403(Throwable e){
        return buildGraphQLError(e, ErrorType.FORBIDDEN);
    }

    public static GraphQLError return404(Throwable e){
        return buildGraphQLError(e, ErrorType.NOT_FOUND);
    }

    public static GraphQLError return500(Throwable e){
        return buildGraphQLError(e, ErrorType.INTERNAL_ERROR);
    }

//==========================================[ Here lies the line for local abstractions ]=====================================================//

    private static GraphQLError buildGraphQLError(Throwable e, ErrorType errorType){
        String cause = "";

        String graphQlErrorMessage = "";

        if(e.getCause() != null){
            cause += e.getCause().getMessage();
        }

        if(errorType == ErrorType.UNAUTHORIZED || errorType == ErrorType.FORBIDDEN || errorType == ErrorType.INTERNAL_ERROR){
            String causeLog = (cause.isEmpty() || cause.isBlank()) ? "" : errorCausePrefix + cause;
            log.error(e.getMessage() + causeLog);
        }

        String[] splitMessage = e.getMessage().split(": ");
        graphQlErrorMessage = splitMessage.length > 1 ? splitMessage[1] : splitMessage[0];

        return GraphqlErrorBuilder.newError().message(graphQlErrorMessage).errorType(errorType).build();
    }
}

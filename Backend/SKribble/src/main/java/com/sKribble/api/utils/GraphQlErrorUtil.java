package com.sKribble.api.utils;

import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;

public class GraphQlErrorUtil {

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
        return GraphqlErrorBuilder.newError().message(e.getMessage()).errorType(errorType).build();
    }

}

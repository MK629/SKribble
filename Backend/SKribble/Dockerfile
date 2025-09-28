#NOTICE: Build the app with 'mvn package' first

#######################[ Environment variables ]#######################
### Set these environment variables in your docker image or in a .env
#       SKRIBBLE_GRAPHIQL_ENDPOINT=/graphiql
#       SKRIBBLE_GRAPHQL_ENDPOINT
#       SKRBBLE_AUTH_ENDPOINT
#       SKRIBBLE_COMMON_ENDPOINT
#       JWT_SECRET_TOKEN_KEY
#       JWT_ISSUER
#       JWT_ENCRYPTION_KEY
#       SKRIBBLE_MONGODB_HOST
#       SKRIBBLE_MONGODB_PORT
#       SKRIBBLE_MONGODB_AUTHDB
#       SKRIBBLE_MONGODB_DB
#       SKRIBBLE_MONGODB_TEST_DB
#       SKRIBBLE_MONGODB_USER
#       SKRIBBLE_MONGODB_PASSWORD
#       SKRIBBLE_MONGODB_URI
#       SKRIBBLE_MONGODB_TEST_URI

FROM openjdk:21

WORKDIR /SKRIBBLE_BACKEND

ARG APP_NAME=SKribble.jar

COPY /target/*.jar ${APP_NAME}

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", ${APP_NAME} ]
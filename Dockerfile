# pull official base image
#ltang: Smallest
FROM eclipse-temurin:17.0.3_7-jre

# set work directory
WORKDIR /app

ARG PROJECT_NAME=markting-service
ARG JAR_FILE=build/libs/${PROJECT_NAME}-*.jar

# https://vsupalov.com/docker-arg-env-variable-guide/#setting-env-values
ENV APP_JAR=${PROJECT_NAME}-app.jar

COPY ${JAR_FILE} ${APP_JAR}

# entry point
ENTRYPOINT java -jar ${APP_JAR}
EXPOSE ${CONTAINER_PORT}

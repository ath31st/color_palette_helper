FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} second_tlg_bot.jar
ENTRYPOINT ("java", "-jar", "/second_tlg_bot.jar")
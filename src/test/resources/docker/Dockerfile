FROM openjdk:8
MAINTAINER zlikun <zlikun-dev@hotmail.com>

ENV UPDATED_AT 2018/06/04

ADD ./docker-user-service-1.0.0.jar /code/user.jar
WORKDIR /code

CMD ["java", "-jar", "user.jar"]

EXPOSE 80
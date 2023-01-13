FROM openjdk:19-jdk-alpine

USER root

RUN addgroup -S micronaut && adduser -S micronaut -G micronaut
USER micronaut:micronaut

VOLUME /tmp

COPY *-all.jar .
COPY class-data .
ENTRYPOINT java --enable-preview -XX:SharedArchiveFile=class-data -jar events-service.jar
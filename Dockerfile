FROM openjdk:19-jdk-alpine

USER root

RUN addgroup -S micronaut && adduser -S micronaut -G micronaut
USER micronaut:micronaut

VOLUME /tmp
ARG VERSION=version
ENV VERSION=${VERSION}

COPY build/libs/*-all.jar .
COPY class-data .
ENTRYPOINT java --enable-preview -XX:SharedArchiveFile=class-data -jar events-$VERSION-all.jar
FROM openjdk:19-jdk-alpine

USER root

RUN addgroup -S micronaut && adduser -S micronaut -G micronaut
USER micronaut:micronaut

VOLUME /tmp
ARG VERSION=0.1.0
ENV VERSION=${VERSION}

COPY *-all.jar .
COPY class-data .
ENTRYPOINT java --enable-preview -XX:SharedArchiveFile=class-data -jar events-$VERSION-all.jar
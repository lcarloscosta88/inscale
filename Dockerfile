FROM openjdk:latest
WORKDIR /opt
ENV PORT 8012
EXPOSE 8012
COPY target/*.jar /opt/test.jar
ENTRYPOINT exec java $JAVA_OPTS -jar test.jar
FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} manager-shiro.jar
ENTRYPOINT ["JAVA","-Djava.security.egd=file:/dev ./urandom","-jar","manager-shiro.jar"]

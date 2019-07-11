FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} manager-shiro.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Ddruid.file=http://47.98.124.74:8002/wfq-dev/managerDatasource.yaml","-jar","/manager-shiro.jar"]
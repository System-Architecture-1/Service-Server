FROM jenkins/jenkins:lts
USER root
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} my-project.jar
ENTRYPOINT ["java","-jar","/my-project.jar"]
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
USER jenkins
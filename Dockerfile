FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080
#Define root directory
WORKDIR /root

#Copy files
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN chmod +x ./mvnw
#Download dependencies
RUN ./mvnw dependency:go-offline

#Copy SRC
COPY ./src /root/src

#BUILD APP
RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java","-jar","/root/target/superhero-backend-0.0.1-SNAPSHOT.jar"]

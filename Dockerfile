# use lightweight OpenJDK 21 runtime image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /deploy

# copy the Spring Boot fat jar into the image
COPY target/freerider-ep-1.0.0.jar /deploy/freerider-ep-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/deploy/freerider-ep-1.0.0.jar"]

# - - - - - - - - - - - - - - - - - - -
# create image:
# - docker build -t freerider-ep-image:1.0 .
# 
# create container:
# - docker run --name freerider-ep-container -p8080:8080 freerider-ep-image:1.0
# 

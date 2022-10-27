FROM openjdk:17

# copy the packaged jar file into our docker image
COPY target/itech-0.0.1-SNAPSHOT.jar /itech.jar

# set the startup command to execute the jar file
CMD ["java", "-jar", "/itech.jar"]
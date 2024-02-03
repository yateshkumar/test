# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Set the JNA library path
ENV MAVEN_OPTS="-Djna.library.path=/usr/local/opt/libffi/lib"

# Copy the current directory contents into the container at /app
COPY ./target/shoppingcart-ms-1.0.0.jar /home/app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define application arguments
CMD ["java", "-jar", "/home/app/app.jar"]


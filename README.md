# Shopping Cart Microservice

This microservice provides functionality for managing shopping carts.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)

## Introduction

The shopping cart microservice allows users to add products to their shopping cart, calculate the cart's state (subtotal, tax, total), and manage their shopping cart efficiently.

## Features

- Add products to the shopping cart.
- Calculate the state of the shopping cart (subtotal, tax, total).
- Handle errors and exceptions gracefully.

## Technologies Used

- Java (1.8)
- Spring Boot (2.7.18)
- Maven (3.9.6)
- JUnit

## Getting Started

To run the microservice locally, follow these steps:

### Configure Properties

Configure the necessary properties in src/main/resources/application.yml or use environment variables. 

### Build and Run

Open a terminal and navigate to the project's root directory.

```
mvn clean install
java -jar target/shoppingcart-ms-1.0.0.jar
```

The microservice will be accessible at http://localhost:8080.

### Docker Approach

#### Build and Run with Docker
```
docker build -t shoppingcart-ms .
docker-compose up
```
The microservice will be accessible at http://localhost:8080.

#### Docker Configuration

Dockerfile
```
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
```

docker-compose.yml
```
version: '3'

services:
  shoppingcart:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
```

## API Endpoints

API endpoints and their functionalities.

- `POST /api/shopping-cart/add-product`: Add a product to the shopping cart.
- `GET /api/shopping-cart/calculate-state/{userId}`: Calculate the state of the shopping cart.

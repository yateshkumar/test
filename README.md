# Shopping Cart Microservice

This microservice provides functionality for managing shopping carts.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Database Choice](#database-choice)


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
- H2 In-Memory Database

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


## Database Choice

### H2 In-Memory Database:

#### Advantages:
- Quick development and testing.
- Lightweight, embedded, and portable.
- Simple configuration for rapid setup.
- Suitable for local testing and demos.
- Considerations for Actual Deployment:

#### Limitations of H2:
- Limited scalability in distributed setups.
- Data not persisted after application shutdown.
- Potential concurrency challenges with substantial data.
- Recommended Choice for Production: Redis

####Advantages of Redis:
- High-performance in-memory data store.
- Horizontal scalability for handling increased data and traffic.
- Persistence options for data durability.
- Versatility in supporting various data structures.
- Atomic operations for data integrity.
- Pub/Sub mechanism for real-time communication.
- Active community support.

In summary, H2 serves well for quick development and local testing due to its simplicity and portability. However, for a robust production solution with scalability and persistence requirements, Redis stands out as the recommended choice. The selection depends on the specific needs and scale of the application.

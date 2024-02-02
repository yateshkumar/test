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
- H2 In-Memory Database (Recommended : **Redis**)

## Getting Started

To run the microservice locally, follow these steps:

### Configure Properties

Configure the necessary properties in ``` src/main/resources/application.yml ``` or use environment variables. 

Example application.yml:
```
product-api:
  base-url: https://equalexperts.github.io/backend-take-home-test-data/
spring:
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  datasource:
    url: jdbc:h2:mem:shopping-cart-ms_DB
    driverClassName: org.h2.Driver
    username: shopping-cart
    password: shopping-cart
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```

### Build and Run

Open a terminal and navigate to the project's root directory.

```
mvn clean package
java -jar target/shoppingcart-ms-1.0.0.jar
```

The microservice will be accessible at http://localhost:8080.

### Docker Approach

#### Build and Run with Docker
```
mvn clean package
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

1. `POST /api/shopping-cart/add-product`

Adds a product to the shopping cart.

### Request Body
Expects a JSON payload with details of the product to be added.

#### Example Request:
```
{
  "userAccountId": 123,
  "productName": "SampleProduct",
  "quantity": 2
}
```
#### Mandatory Parameters:
- **userAccountId** : Existing account id of the logged-in user.
- **productName** : Name of the product to be added in the shopping cart.
- **quantity** : Quantity of the product to be added in the shopping cart.

#### Success Response:
Returns the updated shopping cart.

#### Example Success Response:
```
{
    "userAccountId": 2,
    "cartItems": [
        {
            "name": "Corn Flakes",
            "price": 2.52,
            "quantity": 2
        },
        {
            "name": "Weetabix",
            "price": 9.98,
            "quantity": 1
        }
    ],
    "cartTotals": {
        "subtotal": 15.02,
        "tax": 1.88,
        "total": 16.90
    }
}
```
### Error Response:
Possible error responses include validation failures, product not found, or internal server errors.

#### Example Error Response:
```
{
    "timestamp": "2024-02-03 02:31:10",
    "status": 400,
    "error": "Bad Request",
    "message": "Invalid value for parameter 'quantity' : '0'"
}
```

```
{
    "timestamp": "2024-02-03 02:31:37",
    "status": 400,
    "error": "Bad Request",
    "message": "Provided quantity: '21' is greater than maximum allowed : '20'"
}
```

2. `GET /api/shopping-cart/{userAccountId}`

Returns the current state of the shopping cart for a specific user that includes all the cart items along with subtotal, tax and total.

### Path Parameter:
- **{userAccountId}** : Existing account id of the logged-in user.

### Example Request:
```
/api/shopping-cart/123
```

### Success Response:
Returns the calculated cart totals.

#### Example Success Response:
```
{
    "userAccountId": 2,
    "cartItems": [
        {
            "name": "Corn Flakes",
            "price": 2.52,
            "quantity": 2
        },
        {
            "name": "Weetabix",
            "price": 9.98,
            "quantity": 1
        }
    ],
    "cartTotals": {
        "subtotal": 15.02,
        "tax": 1.88,
        "total": 16.90
    }
}
```

### Error Response:
Possible error responses include shopping cart not found or internal server errors.

#### Example Error Response:
```
{
    "timestamp": "2024-02-03 02:30:40",
    "status": 404,
    "error": "Not Found",
    "message": "Shopping cart is empty."
}
```

## Database Choice

### H2 In-Memory Database:

#### Advantages:
- Quick development and testing.
- Lightweight, embedded, and portable.
- Simple configuration for rapid setup.
- Suitable for local testing and demos.

#### Limitations of H2:
- Limited scalability in distributed setups.
- Data not persisted after application shutdown.
- Potential concurrency challenges with substantial data.
- Recommended Choice for Production: **Redis**

#### Advantages of Redis:
- Horizontal scalability for handling increased data and traffic.
- Persistence options for data durability.
- Versatility in supporting various data structures.
- Atomic operations for data integrity.
- Pub/Sub mechanism for real-time communication.
- Active community support.

In summary, H2 serves well for quick development and local testing due to its simplicity and portability. However, for a robust production solution with scalability and persistence requirements, Redis stands out as the recommended choice. The selection depends on the specific needs and scale of the application.

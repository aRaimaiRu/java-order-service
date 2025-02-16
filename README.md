# Order Product Service

This is a Spring Boot application for managing orders and products in an e-commerce system.

## Table of Contents

- [Order Product Service](#order-product-service)
    - [Table of Contents](#table-of-contents)
    - [Features](#features)
    - [Technologies Used](#technologies-used)
    - [Getting Started](#getting-started)
        - [Prerequisites](#prerequisites)
        - [Installation](#installation)
        - [Running the Application](#running-the-application)
        - [Running Tests](#running-tests)
    - [API Endpoints](#api-endpoints)
        - [Order Endpoints](#order-endpoints)
        - [Product Endpoints](#product-endpoints)
    - [Configuration](#configuration)
    - [Swagger Documentation](#swagger-documentation)
    - [License](#license)

## Features

- Create, update, delete, and retrieve orders
- Create, update, delete, and retrieve products
- Validation for request payloads
- Exception handling for common errors

## Technologies Used

- Java 17
- Spring Boot 3.4.2
- Spring Data JPA
- H2 Database (for testing)
- PostgreSQL (for development)
- Maven
- Docker
- Swagger

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker (for running PostgreSQL)

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/aRaimaiRu/order-product-service.git
   cd order-product-service
   ```

2. Build the project:
   ```sh
   mvn clean install
   ```

### Running the Application

1. Start the PostgreSQL database using Docker:
   ```sh
   docker-compose up -d
   ```

2. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```

### Running Tests

To run the tests, use the following command:

```sh
mvn test
```

## API Endpoints

### Order Endpoints

- **GET /api/v1/orders**: Retrieve all orders
- **GET /api/v1/orders/{id}**: Retrieve an order by ID
- **POST /api/v1/orders**: Create a new order
- **PUT /api/v1/orders/{id}**: Update an existing order
- **DELETE /api/v1/orders/{id}**: Delete an order

### Product Endpoints

- **GET /api/v1/products**: Retrieve all products
- **GET /api/v1/products/{id}**: Retrieve a product by ID
- **POST /api/v1/products**: Create a new product
- **PUT /api/v1/products/{id}**: Update an existing product
- **DELETE /api/v1/products/{id}**: Delete a product

## Configuration

The application uses different configurations for development and testing environments. The configuration files are
located in the `src/main/resources` directory.

- `application.yml`: Main configuration file
- `application-dev.yml`: Development configuration
- `application-test.yml`: Test configuration

## Swagger Documentation

Swagger UI is available to visualize and interact with the API's resources.

- **URL**: `http://localhost:8088/swagger-ui.html`

## License

This project is licensed under the MIT License.

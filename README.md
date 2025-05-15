# Books API - Micronaut REST Service

A modern RESTful API service built with Micronaut Framework and Kotlin, featuring comprehensive API documentation through OpenAPI/Swagger.

## Overview

This project demonstrates a fully functional microservice that provides CRUD operations for a book collection. It showcases best practices for building REST APIs with Micronaut, including proper request/response modeling, validation, error handling, and API documentation.

## Features

- **RESTful API** - Complete implementation of CRUD operations for books
- **OpenAPI Documentation** - Interactive API docs with Swagger UI
- **Validation** - Request payload validation with meaningful error messages
- **Error Handling** - Proper HTTP status codes and error responses
- **Docker Support** - Ready to deploy with Docker and Docker Compose
- **Health Endpoint** - Monitoring endpoint for container health checks
- **CORS Configuration** - Cross-origin resource sharing enabled
- **Logging** - Structured logging configured with logback

## Requirements

- JDK 11 or higher
- Docker (optional, for containerized deployment)

## Running Locally

### Building the Application

```bash
./gradlew build
```

### Running the Application

```bash
./gradlew run
```

The application will start on port 8000 by default.

## API Endpoints

The service exposes the following endpoints:

| Method | URL                 | Description                          |
|--------|---------------------|--------------------------------------|
| GET    | /books              | List all books                       |
| GET    | /books/{id}         | Get a specific book by ID            |
| POST   | /books              | Create a new book                    |
| PUT    | /books/{id}         | Update an existing book              |
| DELETE | /books/{id}         | Delete a book                        |
| GET    | /health             | Service health check                 |

## API Documentation

The API is fully documented using OpenAPI/Swagger. When the application is running, you can access:

- **Swagger UI**: [http://localhost:8000/swagger-ui/](http://localhost:8000/swagger-ui/)
- **OpenAPI Specification**: [http://localhost:8000/swagger/books-api-1.0.yml](http://localhost:8000/swagger/books-api-1.0.yml)

## Docker Deployment

### Building the Docker Image

```bash
docker build -t books-api .
```

### Running with Docker Compose

```bash
docker-compose up -d
```

This will start the service on port 8000 (or the port configured in docker-compose.yml).

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── kotlin/com/example/
│   │   │   ├── controller/       # REST API controllers
│   │   │   ├── model/            # Data models and DTOs
│   │   │   ├── service/          # Business logic
│   │   │   └── Application.kt    # Main application class
│   │   └── resources/
│   │       ├── application.yml   # Application configuration
│   │       └── logback.xml       # Logging configuration
│   └── test/                     # Unit and integration tests
├── Dockerfile                    # Docker build file
├── docker-compose.yml            # Docker Compose configuration
└── build.gradle.kts              # Gradle build configuration
```

## Development

### Testing

Run the tests with:

```bash
./gradlew test
```

### OpenAPI Generation

The OpenAPI specification is automatically generated during the build process. When you modify the controllers or models, the OpenAPI documentation will be updated accordingly.

## Key Technologies

- **Micronaut 3.10.1**: A modern, JVM-based, full-stack framework for building microservices
- **Kotlin 1.6.21**: A statically typed programming language that runs on the JVM
- **OpenAPI/Swagger**: API documentation and UI
- **Docker**: Containerization for deployment

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
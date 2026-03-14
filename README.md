# Student Service

This is a Spring Boot microservice for managing student records. It is part of the Skiply system and integrates with a Eureka Discovery Server for service registration and discovery.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete student records.
- **Pagination**: Support for paginated retrieval of student lists.
- **Service Discovery**: Registers itself with Eureka Server.
- **API Documentation**: Integrated Swagger UI for API testing and documentation.
- **In-Memory Database**: Uses H2 for development and testing.

## Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.5.x
- **Service Discovery**: Spring Cloud Netflix Eureka Client
- **Persistence**: Spring Data JPA
- **Database**: H2 (In-memory)
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Utilities**: Lombok

## Configuration

The application is configured to run on port `8081` by default.

### Eureka Server
The service attempts to register with a Discovery Server at:
`http://localhost:9090/eureka/`

### Database
- **URL**: `jdbc:h2:mem:skiplydb`
- **H2 Console**: `http://localhost:8081/h2-console`
- **Credentials**: `sa` / `password`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/students` | Add a new student |
| GET | `/api/v1/students/{studentId}` | Get student by ID |
| GET | `/api/v1/students` | Get all students (paginated) |
| PUT | `/api/v1/students/{studentId}` | Update an existing student |
| DELETE | `/api/v1/students/{studentId}` | Delete a student |

## Getting Started

### Prerequisites
- JDK 21
- Maven 3.x

### Running the Application
1. Ensure the Eureka Discovery Server is running at `http://localhost:9090`.
2. Navigate to the project root directory.
3. Run the following command:
   ```bash
   mvn spring-boot:run
   ```

### API Documentation
Once the application is running, you can access the Swagger UI at:
`http://localhost:8081/swagger-ui.html`

# Price Calculator Service

## Overview

This service provides an implementation for handling price calculations based on different criteria. The architecture follows **Hexagonal Architecture
** principles to ensure better decoupling and maintainability. Additionally, a base structure using **interfaces, abstract classes, and generics** has
been implemented to facilitate the addition of new entities, making it easy to create generic repositories, services, and controllers for CRUD
operations.

## Technologies Used

- **Java 21**
- **Spring Boot 3.2.4**
- **H2 in-memory database**
- **Spring Data JPA**
- **Spring Validation**
- **MapStruct** (for DTO to Entity mapping)
- **Lombok** (to reduce boilerplate code)
- **JUnit 5**, **Mockito** and **AssertJ** (for unitary testing)
- **Rest Template** (for integration testing)
- **SpringDoc OpenAPI** (for API documentation)

## Running the Application Locally

### Prerequisites

Ensure you have **Maven** installed and available in your system.

### Build and Run

```sh
mvn clean compile spring-boot:run
```

### Important URLs for Local Environment

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - Provides an easy way to interact with the API endpoints.
    - The main exercise endpoint: **`POST /price-controller/price/priority`**

- **H2 Database Console:** [http://localhost:8080/h2-console/login.jsp](http://localhost:8080/h2-console/login.jsp)
    - Useful for direct database inspection.
    - Credentials:
        - **Driver Class:** `org.h2.Driver`
        - **JDBC URL:** `jdbc:h2:mem:retaildb`
        - **User Name:** `sa`
        - **Password:** (leave blank)

## Database Design

The service is backed by an **H2 in-memory database**, as required.

- Several additional tables have been created.
- Constraints and indexes have been applied to optimize queries and improve search performance.
- The initial dataset provided in the exercise is **loaded via an SQL file** when the application starts. The mentioned file can be
  found [here](src/main/resources/data/data.sql).

## Compilation Requirement for MapStruct

Before running the application, it's **imperative** to compile it first:

```sh
mvn compile
```

This ensures that **MapStruct** generates the necessary mapper implementations required for DTO-to-Entity conversions.

## Testing

### Unitary Tests

Unit tests have been implemented for service and controller layers using **JUnit 5**, **Mockito**, and **AssertJ** to ensure the correctness and
reliability of the application's core logic. These tests focus on validating business rules and request handling while isolating dependencies through
mocking. The **entity model** and **DTO classes** have been excluded from unit testing, as they primarily serve as data structures without business
logic.

### Integration Tests

Integration tests for the [PriceController](src/test/java/org/gzunzu/adapter/api/controllers/PriceControllerIT.java) are implemented as per the
exercise requirements.

- These can be executed by running the test class directly.
- Alternatively, they will run automatically during the Maven build process:

```sh
mvn install
```

## Suggested Improvements

The following improvements could further enhance the project's maintainability, scalability, and security:

- Implement **Pagination in Controller Responses**: Introduce pagination for API responses using a standard Spring library, such as Spring Data
  Pageable. This would optimize performance by preventing large data loads and improving client-side data handling.
- Integrate **Authentication and Authorization**: Store user credentials in the database and implement authentication and authorization mechanisms
  using Spring Security. This would ensure secure access to protected resources, enforcing proper credential validation and role-based access
  control (RBAC) in controllers.
- **Domain Layer Isolation:** Introduce a separate domain layer between DTOs and Entities to fully isolate domain models from database structures and
  request parameters.
- **High Load Handling:** If the application needs to handle a high volume of incoming HTTP requests, consider implementing a **message queue system (
  e.g., Kafka)**. This would allow results to be published to a topic, with clients receiving an identifier to later retrieve results asynchronously.

## Conclusion

This service is structured for **extensibility, maintainability, and scalability** while adhering to modern software architecture principles. The
usage of generics, layered architecture, and structured database design ensures that additional functionalities can be integrated seamlessly.

---
Developed as part of a technical assessment. Requirements can be found [here](src/main/resources/docs/TestJava2020.txt).
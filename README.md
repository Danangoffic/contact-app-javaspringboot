# Contact Management API

A Spring Boot application that provides a RESTful API for managing contacts with CRUD operations, search functionality, and API documentation.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete contacts
- **Search Functionality**: Search contacts by name, email, or phone number
- **Data Validation**: Validation for required fields and unique email addresses
- **API Documentation**: Swagger/OpenAPI documentation available at `/swagger-ui.html`
- **RESTful API**: Following standard REST conventions

## Technology Stack

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **MySQL Database**
- **Spring Web MVC**
- **SpringDoc OpenAPI (Swagger)**
- **Lombok**

## Project Structure

```
src/
├── main/
│   ├── java/com/app/contact/
│   │   ├── ContactApplication.java
│   │   ├── model/
│   │   │   └── Contact.java
│   │   ├── repository/
│   │   │   └── ContactRepository.java
│   │   ├── service/
│   │   │   └── ContactService.java
│   │   └── controller/
│   │       └── ContactController.java
│   └── resources/
│       └── application.properties
```

## API Endpoints

- `GET /api/contacts` - Get all contacts (with optional search keyword)
- `GET /api/contacts/{id}` - Get a specific contact by ID
- `POST /api/contacts` - Create a new contact
- `PUT /api/contacts/{id}` - Update an existing contact
- `DELETE /api/contacts/{id}` - Delete a contact
- `GET /api/contacts/search?keyword={keyword}` - Search contacts

## Database Configuration

The application is configured to connect to a MySQL database with the following settings in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/contactdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
```

## Running the Application

1. Make sure you have Java 17+ and Maven installed
2. Set up a MySQL database named `contactdb`
3. Update the database credentials in `application.properties` if needed
4. Run the application using Maven:

```bash
./mvnw spring-boot:run
```

The application will start on port 8080 by default.

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui.html`
OpenAPI documentation is available at: `http://localhost:8080/v3/api-docs`

## Database Schema

The application uses a single `contact` table with the following columns:
- `id` (Primary Key, Auto-increment)
- `name` (VARCHAR 100, Not Null)
- `email` (VARCHAR 150, Unique, Not Null)
- `phone` (VARCHAR 20)
- `address` (VARCHAR 255)
- `created_at` (DateTime)
- `updated_at` (DateTime)
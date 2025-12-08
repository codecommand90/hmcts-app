# HMCTS Task Management Application

A full-stack task management application built with Spring Boot (backend) and Angular (frontend), designed for creating and managing tasks with validation, error handling, and API documentation.

## 🚀 Features

- **Task Management**: Create tasks with title, description, status, and due date/time
- **Input Validation**: Client-side and server-side validation
- **API Documentation**: Interactive Swagger UI for API testing
- **Clean Architecture**: Separation of concerns with service, repository, and controller layers
- **Exception Handling**: Global exception handler with consistent error responses
- **Database Integration**: PostgreSQL database with JPA/Hibernate
- **CORS Configuration**: Properly configured for frontend-backend communication

## 🛠️ Technologies

### Backend
- **Spring Boot 4.0.0** - Java application framework
- **Java 17** - Programming language
- **PostgreSQL 15** - Database
- **Spring Data JPA** - Data persistence
- **Spring Security** - Security framework
- **SpringDoc OpenAPI 2.7.0** - API documentation (Swagger)
- **Maven** - Build tool
- **Docker Compose** - Database containerization

### Frontend
- **Angular 17** - Frontend framework
- **TypeScript 5.2.2** - Programming language
- **RxJS 7.8.0** - Reactive programming
- **Angular Forms** - Form handling and validation

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 18+** and **npm**
- **Docker** and **Docker Compose** (for database)
- **Angular CLI 17+** (`npm install -g @angular/cli`)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd hmcts-app
```

### 2. Database Setup

Start the PostgreSQL database using Docker Compose:

```bash
cd backend
docker-compose up -d
```

This will start a PostgreSQL container with the following configuration:
- **Host**: localhost
- **Port**: 5432
- **Database**: hmctsdatabase
- **Username**: postgres
- **Password**: password

### 3. Backend Setup

Navigate to the backend directory and set environment variables:

```bash
cd backend
export DB_URL=localhost
export DB_PORT=5432
export DB_NAME=hmctsdatabase
export DB_USERNAME=postgres
export DB_PASSWORD=password
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will start on **http://localhost:8080**

### 4. Frontend Setup

Navigate to the frontend directory and install dependencies:

```bash
cd frontend
npm install
```

Start the Angular development server:

```bash
npm start
```

The frontend will start on **http://localhost:4200**

## 📚 API Documentation

Once the backend is running, you can access the Swagger UI at:

**http://localhost:8080/swagger-ui/index.html**

The API documentation includes:
- Interactive API testing interface
- Request/response schemas
- Example values
- Error response documentation

### API Endpoints

#### Create Task
- **URL**: `POST /api/v1/tasks`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "title": "Task title (required, max 200 characters)",
  "description": "Task description (optional, max 1000 characters)",
  "status": "PENDING | IN_PROGRESS | COMPLETED | CANCELLED",
  "dueDateTime": "2024-12-31T12:00:00"
}
```
- **Success Response** (201 Created):
```json
{
  "id": "uuid",
  "title": "Task title",
  "description": "Task description",
  "status": "PENDING",
  "dueDateTime": "2024-12-31T12:00:00",
  "createdAt": "2024-01-01T10:00:00"
}
```

## 🧪 Testing

### Backend Tests

Run backend unit tests:

```bash
cd backend
mvn test
```

The test suite includes:
- `TaskServiceTest` - Service layer tests
- `TaskControllerTest` - Controller layer tests
- `TaskMapperTest` - Mapper tests

### Frontend Tests

Run frontend unit tests:

```bash
cd frontend
npm test
```

## 📁 Project Structure

```
hmcts-app/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codecomand/hmcts_app/
│   │   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── exception/      # Exception handling
│   │   │   │   ├── task/            # Task domain
│   │   │   │   │   ├── request/     # Request DTOs
│   │   │   │   │   ├── response/   # Response DTOs
│   │   │   │   │   └── *.java       # Entity, Service, Controller, etc.
│   │   │   │   └── common/          # Common entities
│   │   │   └── resources/
│   │   │       └── application.yaml # Application configuration
│   │   └── test/                    # Unit tests
│   ├── docker-compose.yml           # Database setup
│   └── pom.xml                      # Maven dependencies
│
└── frontend/
    ├── src/
    │   ├── app/
    │   │   ├── models/              # TypeScript models
    │   │   ├── services/            # API services
    │   │   ├── task-form/           # Task form component
    │   │   └── app.component.ts     # Root component
    │   └── main.ts                  # Application entry point
    └── package.json                 # npm dependencies
```

## 🔧 Configuration

### Backend Configuration

The backend configuration is in `backend/src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: hmcts-application
  datasource:
    url: jdbc:postgresql://${DB_URL}:${DB_PORT}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

### Environment Variables

Set these environment variables before running the backend:

- `DB_URL` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 5432)
- `DB_NAME` - Database name (default: hmctsdatabase)
- `DB_USERNAME` - Database username (default: postgres)
- `DB_PASSWORD` - Database password (default: password)

## 🐛 Error Handling

The application implements comprehensive error handling:

- **Business Exceptions**: Custom exceptions with error codes
- **Validation Errors**: Field-level validation error messages
- **Global Exception Handler**: Consistent error response format

Error response format:
```json
{
  "code": "ERROR_CODE",
  "message": "Error message",
  "httpStatus": "BAD_REQUEST",
  "fieldErrors": [
    {
      "field": "fieldName",
      "message": "Validation error message"
    }
  ]
}
```

## 🔒 Security

- Spring Security is configured with CORS support
- API endpoints are publicly accessible (configured for development)
- CSRF protection is disabled for API endpoints
- CORS is configured to allow requests from `http://localhost:4200`

## 📝 Development Guidelines

### Code Style
- Follow clean code principles
- Use meaningful variable and method names
- Implement proper exception handling
- Write unit tests for business logic

### API Design
- Use RESTful conventions
- Implement proper HTTP status codes
- Provide clear error messages
- Document APIs with Swagger annotations

## 🚀 Deployment

### Backend
1. Build the application: `mvn clean package`
2. Run the JAR: `java -jar target/hmcts-app-0.0.1-SNAPSHOT.jar`
3. Ensure environment variables are set

### Frontend
1. Build for production: `npm run build`
2. Serve the `dist/` folder using a web server (nginx, Apache, etc.)



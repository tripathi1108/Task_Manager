# Task Management System

## Overview
The **Task Management System** is a web-based application designed to streamline task organization and tracking. It enables users to create, update, delete, and manage tasks efficiently. Built with **Spring Boot**, the system provides RESTful APIs for task and user management.

## Features

### Task Management
- ✅ **Create Task** – Users can add new tasks to the system.
- ✅ **Update Task** – Modify details of an existing task.
- ✅ **Delete Task** – Remove tasks when no longer needed.
- ✅ **Retrieve All Tasks** – Fetch a comprehensive list of all tasks.
- ✅ **Retrieve Task by ID** – Obtain details of a specific task using its unique identifier.
- ✅ **Filter Tasks by Status** – View tasks based on their current status.
- ✅ **Paginated Task Retrieval** – Load tasks in chunks for better performance.

### User Management
- ✅ **Register User** – Add new users to the system.
- ✅ **Update User Information** – Modify existing user details.
- ✅ **Delete User** – Remove user accounts when necessary.
- ✅ **Retrieve User by ID** – Access details of a specific user.

## Technology Stack
- **Java 17+** – Core programming language.
- **Spring Boot 3+** – Framework for backend API development.
- **Spring Security & JWT** – Authentication and authorization.
- **PostgreSQL** – Relational database for data persistence.
- **JPA (Java Persistence API)** – ORM for database interactions.
- **Swagger** – API documentation tool.
- **Maven** – Dependency and project management.
- **JUnit & Mockito** – Testing frameworks for unit and integration tests.

## API Endpoints

### Task APIs
#### Create a Task
**POST** `/api/tasks`
- Request Body:
  ```json
  {
    "title": "this is my task",
    "description": "this is harsh",
    "status": "Pending",
    "userId": userId
  }
  ```
- Response: Returns the created task.

#### Retrieve All Tasks
**GET** `/api/tasks`
- Response: List of tasks.

#### Retrieve a Task by ID
**GET** `/api/tasks/{id}`
- Response: Task details.

#### Update a Task
**PUT** `/api/tasks/{id}`
- Request Body:
  ```json
  {
    "title": "Updated task Manager",
    "description": "Task management process update",
    "status": "COMPLETED"
  }
  ```
- Response: Updated task details.

#### Delete a Task
**DELETE** `/api/tasks/{id}`
- Response: Success message.

### User APIs
#### Create a User
**POST** `/api/users`
- Request Body:
  ```json
  {
    "firstName": "Arsh",
    "lastName": "Tripathi",
    "timezone": "UTC",
    "active": true
  }
  ```
- Response: Newly created user details.

#### Retrieve User by ID
**GET** `/api/users/{id}`
- Response: User details.

#### Update User Information
**PUT** `/api/users/{id}`
- Request Body:
  ```json
  {
    "firstName": "Arsh",
    "lastName": "Tripathi",
    "timezone": "UTC",
    "active": true
  }
  ```
- Response: Updated user details.

#### Delete a User
**DELETE** `/api/users/{id}`
- Response: Success message.

## Unit Testing & Code Coverage
To maintain reliability, unit tests are implemented using **JUnit** and **Mockito**.

### Running Tests & Generating Reports
1. **Execute Unit Tests**
   ```sh
   mvn clean test
   ```
2. **Generate Test Execution Report**
   ```sh
   mvn surefire-report:report
   ```
   - Report at: `target/site/surefire-report.html`

3. **Generate Code Coverage Report (JaCoCo)**
   ```sh
   mvn clean verify
   ```
   - Coverage report at: `target/site/jacoco/index.html`

## Setup Guide

### Prerequisites
- **Java 17+**
- **Spring Boot 3+**
- **Maven**
- **PostgreSQL**

### Installation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/tripathi1108/Task_Manager
   cd task-management-system
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Build and start the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. Access API documentation at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Running the Application
1. Ensure PostgreSQL is running.
2. Verify database configurations in `application.properties`.
3. Start the application using:
   ```sh
   mvn spring-boot:run
   ```
4. Use **Swagger UI** or **Postman** to interact with the APIs.

## Contribution Guidelines
Contributions are encouraged! Follow these steps:
1. **Fork the repository** on GitHub.
2. **Create a new feature branch** for your changes.
3. **Make your modifications** and commit them.
4. **Push changes** to your branch and submit a **pull request**.

Happy coding! 🚀


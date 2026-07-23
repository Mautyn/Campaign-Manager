# Campaign Manager

A simple mini-application demonstrating basic CRUD operations for managing marketing campaigns. Built with a Java/Spring Boot backend and a React frontend.

##  Tech Stack

**Backend:**
* Java 17
* Spring Boot 4.1.0 (WebMVC, Data JPA, Validation)
* H2 Database (In-memory)
* JUnit 5 & Mockito

**Frontend:**
* React & Vite
* React-Select
* Vitest & React Testing Library
* Native CSS (with Dark/Light mode support)

##  Features
* **CRUD Operations:** Create, read, update, and delete marketing campaigns.
* **Basic Validation:** Simple form validation on both client and server sides.
* **Dark Mode:** UI automatically adapts to system preferences.
* **Unit Tests:** Basic test coverage for backend controllers/services and frontend components.

## Local Development Setup

### 1. Backend
1. Open the root directory in your terminal.
2. Run the Spring Boot server:
   ```bash
   ./mvnw spring-boot:run
   3. The API will run on `http://localhost:8080`.

### 2. Frontend
1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies and start the dev server:
   ```bash
   npm install
   npm run dev
   ```
3. The app will be available at `http://localhost:5173`.

## Tests
* **Backend:** Run `./mvnw test` in the root directory.
* **Frontend:** Run `npm run test` in the `/frontend` directory.

---

# **Flight Booking WebFlux**

A reactive backend system for flight inventory, search, and booking built with Spring Boot WebFlux, Reactive MongoDB, Reactor, JUnit, SonarCloud, and JMeter.

---

## **Overview**

This application provides a fully non-blocking, reactive flight booking service featuring:

* WebFlux + Netty reactive I/O
* Reactive MongoDB CRUD operations
* REST APIs for airlines, flights, and bookings
* Request validation and custom exception handling
* JUnit 5 + Mockito test coverage
* JMeter load testing support
* SonarCloud static code analysis

---

## **Features**

### **Reactive Architecture**

* Built with Spring WebFlux
* Uses Reactor (`Mono`, `Flux`)
* Netty event-loop concurrency

### **Modules**

* **Airline Management** – Add and list airlines
* **Flight Inventory** – Add and validate flights
* **Flight Search** – Search based on source, destination, and date
* **Booking System** – Book flights and auto-generate PNR
* **Exception Handling** – Centralized `GlobalErrorHandler`

---

## **Technology Stack**

| Component         | Technology                     |
| ----------------- | ------------------------------ |
| Backend Framework | Spring Boot WebFlux            |
| Reactive Engine   | Reactor (Mono/Flux)            |
| Database          | MongoDB Reactive Driver        |
| Testing           | JUnit 5, Mockito, StepVerifier |
| Code Quality      | SonarCloud                     |
| Load Testing      | Apache JMeter                  |
| Build & CI        | Maven, GitHub Actions          |

---

## **Project Structure**

```
src/
 ├── main/java/com.flightapp
 │    ├── controller
 │    ├── service
 │    ├── service/impl
 │    ├── repository
 │    ├── dto
 │    ├── entity
 │    ├── exception
 │    ├── config
 │    └── util
 └── test/java/com.flightapp
      ├── controller
      ├── service
      ├── repository
      └── TestDataFactory
```
---
##  API Endpoints

### *Add Flight Inventory*

POST /api/v1.0/flight/airline/inventory

### *Search Flights*

POST /api/v1.0/flight/search

### *Book Ticket*

POST /api/v1.0/flight/booking/{flightId}

### *Get Ticket by PNR*

GET /api/v1.0/flight/ticket/{pnr}

### *Booking History*

GET /api/v1.0/flight/booking/history/{emailId}

### *Cancel Ticket*

DELETE /api/v1.0/flight/cancel/{pnr}

---
---

## **Running the Application**

### **1. Clone the Repository**

```
git clone <repo-url>
cd FlightBookingWebFlux
```

### **2. Start MongoDB**

MongoDB must be running at:

```
mongodb://localhost:27017
```

### **3. Build and Run**

```
mvn clean install
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

---

## **Running Tests**

### **Unit Tests + Coverage**

```
mvn clean verify
```

### **Jacoco Report**

Generated at:

```
target/site/jacoco/index.html
```

---

## **SonarCloud Integration**

This project includes:

* `sonar.projectKey`
* `sonar.organization`
* `sonar.host.url`
* `sonar.coverage.jacoco.xmlReportPaths`

GitHub Actions will generate:

* Test execution
* Coverage reports
* SonarCloud scan

---

## **Load Testing with JMeter**

Load scenarios included:

* 20 users
* 50 users
* 100 users

---

## **Configuration**

Application configuration file:

```
src/main/resources/application.properties
```

Includes:

* MongoDB settings
* Server port
* Logging configuration

---



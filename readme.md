Flight Booking WebFlux

A reactive Spring Boot service for managing flights, searching availability, booking tickets, and viewing booking history. The project uses WebFlux, Reactive MongoDB, Reactor, JUnit 5, Mockito, and JaCoCo for coverage.

Features

Add flight inventory

Search flights based on route and date

Book tickets

Fetch ticket details using PNR

View booking history

Cancel tickets

Global error handling

Reactive MongoDB

Full unit tests for service and controller layers

JaCoCo coverage reports

Tech Stack

Spring Boot 3

Spring WebFlux

Reactive MongoDB

Reactor

Lombok

JUnit 5

Mockito

WebTestClient

JaCoCo

Maven

API Endpoints
Add Inventory

POST /api/v1.0/flight/airline/inventory

Search Flights

POST /api/v1.0/flight/search

Book Ticket

POST /api/v1.0/flight/booking/{flightId}

Get Ticket by PNR

GET /api/v1.0/flight/ticket/{pnr}

Booking History

GET /api/v1.0/flight/booking/history/{emailId}

Cancel Ticket

DELETE /api/v1.0/flight/cancel/{pnr}
# Spring Orders System (MongoDB)

**Spring Orders System** is a backend learning project developed in **Java** using **Spring Boot** and **MongoDB**.  
The goal of this project is to practice **clean REST API design**, **Domain-Driven Design (DDD) concepts**, and **real-world backend patterns**, focusing on clear responsibilities, validation, and evolvable architecture.

---

## Features Implemented

- Client, Product, and Order management
- Order creation using request DTOs
- Order item modeling using a **Value Object (`OrderItem`)**
- Price and total calculation using `BigDecimal`
- Validation using Bean Validation (`@Valid`, `@NotNull`, `@NotBlank`, etc.)
- Business rule validation with proper HTTP status codes
- Pagination support with Spring Data `Pageable`
- MongoDB integration with Spring Data
- Clear separation between Controller, Service, and Domain layers

---

## Tech Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/mongodb-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![Maven](https://img.shields.io/badge/MAVEN-000000?style=for-the-badge&logo=apachemaven&logoColor=blue)
![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/vasquesandre)

---

## How It Works

1. A **Client** is created
2. **Products** are registered
3. An **Order** is created for a client using a request DTO
4. Products are added to the order via a list of items:
   - Each item references a product by ID
   - Quantity validation is applied
   - Final price is calculated during order creation
5. Orders can be listed using pagination
6. Orders can be retrieved by ID or by client

---

## Project Structure

- **Controllers**
  - Handle HTTP requests and responses
  - Validate input using DTOs
- **Services**
  - Encapsulate business rules
  - Coordinate domain logic and repositories
- **Domain (Entities)**
  - `Client`
  - `Product`
  - `Order`
- **Value Objects**
  - `OrderItem`
- **DTOs**
  - `CreateClientRequest`
  - `CreateProductRequest`
  - `CreateOrderRequest`
  - `CreateOrderItemRequest`
  - `UpdateProductRequest`
  - `UpdateClientRequest`
  - `ClientResponse`
  - `OrderResponse`
  - `ProductResponse`
- **Repositories**
  - Spring Data MongoDB repositories

---

## Architectural Decisions

- **DDD-inspired design**
  - `Order` as Aggregate Root
  - `OrderItem` as a Value Object
- **DTO-based API**
  - Domain entities are not exposed directly
- **Service layer**
  - Controllers remain thin
  - Business logic centralized
- **MongoDB document modeling**
  - Flexible structure for evolving requirements
- **BigDecimal for monetary values**
  - Avoids floating-point precision issues

---

## Validation Strategy

- Bean Validation at API boundaries
- Business rule validation inside Services
- Clear and explicit HTTP error responses
- Empty results return empty pages instead of exceptions where applicable

---

## AI Usage

AI was used as a **development assistant** to:
- Review architectural decisions
- Validate best practices
- Improve code readability and structure
- Support refactoring and design evolution

---

## Overview

This project consolidates key backend concepts such as:

- RESTful API design
- DTO validation and mapping
- Domain-driven modeling
- MongoDB integration with Spring Data
- Pagination and filtering
- Clean architecture principles

It serves as a solid foundation for future improvements, including:
- Advanced business rules
- Integration and unit tests
- Security and authentication
- Cloud deployment and scalability

---

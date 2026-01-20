# Spring Orders System (MongoDB)

**Spring Orders System** is a backend learning project developed in **Java** using **Spring Boot** and **MongoDB**.  
The main goal of this project is to practice **Domain-Driven Design (DDD) concepts**, **DTO-based APIs**, and **document-oriented modeling**, focusing on clean code, clear responsibilities, and real-world backend patterns.

---

## Features Implemented

- Client, Product, and Order creation
- Order creation using request DTOs
- Order item management using a **Value Object (`OrderItem`)**
- Calculation of final price using `BigDecimal`
- Validation of business rules at API boundaries
- Prevention of invalid orders (missing client, empty items, invalid products)
- Pagination support for listing orders
- MongoDB integration with Spring Data

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
   - Quantity and final price are calculated during order creation
5. Orders can be listed with pagination
6. Orders can be retrieved by ID or by client

---

## Project Structure

- **Entities (Domain)**:
  - `Client`
  - `Product`
  - `Order`
- **Value Objects**:
  - `OrderItem`
- **DTOs**:
  - `CreateOrderRequest`
  - `CreateOrderItemRequest`
  - `OrderRequest`
- **Repositories**:
  - Spring Data MongoDB repositories
- **Controllers**:
  - Responsible for request handling and orchestration

> The project intentionally does not use a Service layer yet, to clearly understand controller responsibilities before refactoring to a full DDD architecture.

---

## Architectural Decisions

- **DDD-inspired modeling**:
  - `Order` is the aggregate root
  - `OrderItem` is a Value Object
- **DTO-based API design** to avoid exposing domain entities
- **BigDecimal** used for all monetary calculations
- **MongoDB document model** chosen for flexibility and aggregation use cases
- Progressive refactoring mindset (controllers → services in future iterations)

---

## Validation Strategy

- Validation at API boundaries (invalid IDs, empty item lists)
- Explicit error handling using HTTP status codes
- Clear separation between request models and domain models

---

## AI Usage

AI was used as a **development assistant** to:
- Review architectural decisions
- Validate DDD concepts
- Improve code clarity and structure
- Support refactoring decisions

---

## Overview

This project consolidates important backend concepts such as:

- REST API design with DTOs
- Domain-driven modeling
- MongoDB integration with Spring Data
- Value Objects and Aggregates
- Monetary precision using BigDecimal
- Clean and evolvable architecture

It serves as a strong foundation for future improvements, such as:
- Introducing a Service layer
- Adding transactional boundaries
- Implementing more complex business rules
- Migrating to a microservices or cloud-based architecture

---

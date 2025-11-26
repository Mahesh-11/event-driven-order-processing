
# Event-Driven Order Processing System (Microservices + Kafka + Spring Boot)

A production-grade **event-driven microservices architecture** built using:

- Java 17  
- Spring Boot 3  
- Apache Kafka  
- Docker & Docker Compose  
- H2 Database (with easy migration to PostgreSQL)  
- Domain-driven design principles  
- Clean, scalable microservices structure  

This project demonstrates a real-world distributed system where microservices communicate asynchronously through Kafka events — following FAANG-level engineering practices.

---

## 🚀 Architecture Overview

The system uses Kafka as the event backbone:

+-----------+ +---------------------+  
| Client | -----> | Order Service |  
+-----------+ +---------------------+  
| |  
| REST / JSON | publishes "orders.created"  
| v  
| +---------------------+  
| | Kafka (broker) |  
| +---------------------+  
| |  
| +--> Payment Service (future)  
| |  
| +--> Inventory Service (future)  


Microservices remain completely decoupled and communicate **only through events**.

---

## 📦 Project Structure

order-processing-microservices/  
│  
├── docker-compose.yml  
│  
└── order-service/  
├── src/main/java  
├── src/main/resources  
├── Dockerfile  
├── pom.xml  


Upcoming modules:

- `payment-service/`
- `inventory-service/`
- `api-gateway/`
- `common/` (shared models + dto)
- `auth-service/` (optional)

---

## 🧩 Implemented Features

### ✔️ Order Service
- REST endpoint to create orders  
- Persists orders with Spring Data JPA  
- Publishes `OrderCreatedEvent` → `orders.created` topic  
- Runs in a dockerized Spring profile  
- Uses domain-layer “OrderDomainService"  

### ✔️ Kafka Infrastructure
- Single-node Kafka with Zookeeper  
- Auto-topic creation (`orders.created`)  
- Health checks ensure Order Service waits for Kafka  

---

## 🐳 Running the Project

### 1. Build the Order Service JAR

cd order-service
mvn clean package -DskipTests
cd ..


### 2. Start the system

docker compose up --build

### 3. Test Order Creation

curl -X POST http://localhost:8081/api/orders

-H "Content-Type: application/json"
-d "{"customerId":"123","productId":"P1","quantity":2}"

You should receive an order JSON response.

---

## 📡 Viewing Kafka Events

Open a shell inside Kafka container:

docker exec -it kafka bash

Run consumer:

kafka-console-consumer
--bootstrap-server kafka:9092
--topic orders.created
--from-beginning

You should see the events published by the Order Service.

---

## 🧱 Tech Stack

- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **Spring Kafka**
- **Kafka + Zookeeper (Confluent Platform 7.5)**
- **Java 17 (Temurin)**
- **Docker Compose**
- **H2 Database (in-memory)**

---

## 📈 Roadmap

### Phase 1 (Next)
- Payment Service (consume → orders.created, publish → orders.paid)  
- Inventory Service  
- Retry + DLQ topics  

### Phase 2
- API Gateway  
- JWT-based Auth Service  
- Shared DTO module  
- Prometheus + Grafana monitoring  

### Phase 3
- GitHub Actions CI/CD  
- Testcontainers integration tests  
- Distributed tracing (OpenTelemetry)  

---

## 🧠 System Design Principles

- Event-driven architecture  
- Asynchronous communication  
- Loose coupling via Kafka  
- Horizontal scalability  
- Idempotent consumers  
- Stateless services  
- Infrastructure-as-code (Docker)  

---

## 🤝 Contributing

Pull requests are welcome!  
Feel free to add new microservices, events, or enhancements.

---

## ⭐ Support

If you found this project helpful, please consider giving it a **⭐ on GitHub**.


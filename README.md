# ⚙️ SpeedyCart: Two-Phase Commit with Spring Boot Microservices

This project demonstrates the **Two-Phase Commit (2PC)** protocol using Spring Boot-based **microservices**, with support from **API Gateway** and **Eureka Discovery Server**.

---

## 🎯 What is Two-Phase Commit (2PC)?

2PC ensures atomic transactions across multiple services. The transaction is split into:

- **Phase 1 (Prepare)**: All participants are asked to "prepare" and confirm they can commit.
- **Phase 2 (Commit/Rollback)**: If all confirm, the coordinator sends a commit. If any fail, it rolls back.

This is useful for scenarios where consistency across distributed systems is critical — like order processing, financial operations, etc.

---

## 🧩 Services in This System

| Service             | Description                                       | Port |
|---------------------|---------------------------------------------------|------|
| 🧾 `order-service`     | Acts as the **2PC Coordinator**                 | 8080 |
| 🥫 `store-service`     | Manages food packet reservation & assignment    | 8081 |
| 🚚 `delivery-service`  | Manages delivery agent reservation & assignment | 8082 |
| 🌐 `api-gateway`       | Single-entry API gateway using Spring Cloud     | 8083 |
| 🧭 `eureka-server`     | Service Discovery via Netflix Eureka            | 8761 |
| 📦 `common-dto`        | Shared DTOs across services                     | N/A  |
---

## ⚙️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Cloud (Eureka, Gateway, OpenFeign)
- Spring Web, Spring Data JPA
- MySQL Database
- MapStruct (Entity ↔ DTO mapping)
- Lombok
- Gradle
- Eureka Discovery Server

---

---

## 🔁 2PC Workflow

1. `order-service` initiates the transaction.
2. **Phase 1** (Prepare):
   - Reserve food packet via `store-service`
   - Reserve delivery agent via `delivery-service`
3. **Phase 2** (Commit/Rollback):
   - If both succeed, assign both (commit)
   - If any fail, release both (rollback)

---

## 🔌 Service Registration

All services register with `eureka-server`. The `api-gateway` uses **service discovery** to route requests dynamically based on service ID.

---

## 🔀 Sample Routes via API Gateway

- `POST /order/api/order/place?orderId=101` → routed to `order-service`
- `POST /store/api/store/reserve` → routed to `store-service`
- `POST /delivery/api/agent/reserve` → routed to `delivery-service`

API Gateway configuration handles these routes using `application.yml`.

---

## 🧪 Sample API Flow

```bash
curl -X POST http://localhost:8080/order/api/order/place?orderId=101



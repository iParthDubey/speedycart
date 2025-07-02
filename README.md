# SpeedyCart - 2 Phase Commit Microservices

This project simulates a distributed transaction using the 2 Phase Commit protocol between three Spring Boot microservices.

## Services

| Service | Port | Description |
|--------|------|-------------|
| `store-service` | 8081 | Manages food packets |
| `delivery-service` | 8082 | Manages delivery agents |
| `order-service` | 8080 | Orchestrates transactions |

## Run Locally

1. Set up MySQL with 3 databases: `storedb`, `deliverydb`, `orderdb`
2. Run each service in separate terminals:

```bash
cd store-service && ./gradlew bootRun
cd delivery-service && ./gradlew bootRun
cd order-service && ./gradlew bootRun
̱
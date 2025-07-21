# Microservices Test Framework

This repository contains a **Java/Spring Boot** based test framework for an e-commerce microservices architecture consisting of **Order Service** and **Payment Service**.

## Features
* Unit, component, contract (Pact), integration, and end-to-end tests organised by service
* **Testcontainers** spins up disposable **PostgreSQL 15** instances for data-driven tests
* **Pact** consumer-driven contracts published to a Pact Broker
* GitHub Actions CI workflow with separate jobs for unit/component, contract verify, and integration stages
* Centralised fixtures (`test-framework` module) provide Testcontainers configuration and Faker-seeded test data
* Example tests demonstrate testing layers:
  * `OrderServiceTest` (unit)
  * `OrderControllerTest` (component with MockMvc)
  * `OrderIntegrationTest` (integration with running Postgres container)

## Prerequisites
* Java 17+
* Maven 3.9+
* Docker 20.10+ (for Testcontainers)

## Running Tests
```bash
mvn clean verify
```
All modules build in a multi-module Maven project. Testcontainers will pull a Postgres image on first run.

## Directory Layout
```
order-service/      # Spring Boot Order microservice & tests
payment-service/    # Spring Boot Payment microservice & tests
test-framework/     # Shared fixtures and utilities
.github/workflows/  # CI pipelines
docker-compose.yml  # Local dev compose file
```

## Local Dev with Docker Compose
```bash
docker compose up -d
```
Runs both services and a Postgres instance exposed on port 5432.

## Technology Choices and Justification
| Tech | Reason |
|------|--------|
| Spring Boot 3 | Rapid REST microservice development, wide ecosystem |
| JUnit 5 | Modern testing features, parameterised tests |
| Testcontainers | Real Dockerised Postgres for reliable integration tests |
| Pact JVM | Industry-standard contract testing |
| Faker | Deterministic fake data generation |
| GitHub Actions | First-class CI for GitHub repos, parallel jobs |

## CI Pipeline Overview
| Job | Stage | Gate |
|-----|-------|------|
| build | Build multi-module Maven project | fail-fast |
| test | Unit & component tests + coverage | 80% min |
| contract | Pact verification | must pass |
| integration | Integration tests with Testcontainers matrix | green |

---
© 2025 Test Framework Example. Licensed under Apache-2.0.

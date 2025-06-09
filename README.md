# Pizza Order Service

This project is a Spring Boot application for managing pizza orders, designed to work as part of an event-driven integration testing framework. It communicates with another service, `pizza-oven-service`, using RabbitMQ for asynchronous messaging.

## Overview

- **Language:** Java 21
- **Build Tool:** Maven
- **Frameworks:** Spring Boot, Spring AMQP, Testcontainers
- **Messaging:** RabbitMQ

## Architecture

- The `pizza-order-service` sends pizza order messages to a RabbitMQ exchange.
- The `pizza-oven-service` (running as a Docker container) processes these orders and sends responses back to a dedicated queue.
- Integration tests verify the end-to-end flow using Testcontainers to manage RabbitMQ and Docker Compose to orchestrate the `pizza-oven-service` container.

## Integration with `pizza-oven-service`

URL: [pizza-oven-service](https://github.com/KilianJimenez/pizza-oven-service)

- The `pizza-oven-service` must be available as a Docker container.
- During integration tests, Docker Compose is used to start the `pizza-oven-service` alongside RabbitMQ.
- The test suite sends orders and waits for responses from the oven service to validate the integration.

## Running Tests

1. Ensure Docker is running on your system.
2. The integration tests will automatically start the required containers using Docker Compose.
3. Run the tests with Maven:

   ```
   mvn clean test
   ```

## Key Components

- **OrderServiceIntegrationTest:** Integration test that sends orders and verifies responses.
- **OvenResponseListener:** Listens for responses from the `pizza-oven-service` via RabbitMQ.
- **RabbitMQConfig:** Configures RabbitMQ queues and exchanges for messaging.

## Requirements

- Java 21
- Maven
- Docker (for running `pizza-oven-service` and RabbitMQ containers)

## Notes

- The `pizza-oven-service` repository is required and must be built as a Docker image for integration testing.
- All messaging is handled via RabbitMQ queues as defined in the application configuration.

---

This project is intended for use as part of a larger event-driven system, with a focus on integration testing between services using message queues and Dockerized components.

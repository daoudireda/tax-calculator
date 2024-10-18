# Tax Calculator

## Overview

This project is a Tax Calculator application built with Java and Spring Boot. It provides endpoints to calculate taxes
based on different strategies and integrates with Kafka for messaging.
I used the Strategy design pattern to implement different tax calculation strategies. The application currently supports
the following strategies:

- Progressive Tax: A tax rate that increases as the taxable amount increases.
- Simple Tax: A fixed tax rate applied to the taxable amount.

## Features

- Calculate tax based on income
- Change tax calculation strategy
- Kafka integration for sending tax calculation results

## Technologies

- Java 21
- Spring Boot
- Maven
- Kafka

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Kafka

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/tax-calculator.git
    cd tax-calculator
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Configuration

Configure Kafka settings in `src/main/resources/application.properties`:

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=tax-calculator-group
```

## launch kafka server

```sh
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

## Kafka Consumer

The application includes a Kafka consumer that listens for messages on the `tax-calculation-topic` topic. The consumer
is implemented in the `TaxCalculatorConsumer` class.
command:

```sh
 kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic tax-calculation-topic --from-beginning
```

## Kafka Producer

The application includes a Kafka producer that sends messages to the `tax-calculation-topic` topic. The producer is
implemented in the `TaxCalculatorProducer` class.
command:

```sh
 kafka-console-producer.sh --broker-list localhost:9092 --topic tax-calculation-topic
```

## Usage

- Calculate tax based on income:
    ```sh
    curl -X POST http://localhost:8080/api/tax/calculate -H "Content-Type: application/json" -d '{"income": 50000}'
    ```

- Change tax calculation strategy:
    ```sh
    curl -X POST http://localhost:8080/api/tax/strategy -H "Content-Type: application/json" -d '{"strategy": "progressive"}'
    ```



Here is a complete guide to install Kafka on Docker, configure it, send and read messages, and navigate the Docker environment.

### Prerequisites

- **Docker**: Ensure Docker is installed on your machine. If not, download and install Docker from [https://www.docker.com/get-started](https://www.docker.com/get-started).

### Step 1: Create a `docker-compose.yml` file

Kafka and Zookeeper work together, and to simplify the installation, we will use Docker Compose. Create a `docker-compose.yml` file in a dedicated directory with the following content:

```yaml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
    depends_on:
      - kafka
```

This `docker-compose.yml` file will configure the following services:

1. **Zookeeper**: Coordination manager for Kafka.
2. **Kafka**: The Kafka broker itself.
3. **Kafka UI**: A web interface to manage Kafka.

### Step 2: Start Kafka with Docker Compose

In the directory where your `docker-compose.yml` file is located, run the following command to start Kafka and Zookeeper:

```bash
docker-compose up -d
```

This will:

- Download the necessary Docker images.
- Start Kafka, Zookeeper, and Kafka UI.

### Step 3: Verify the services

You can verify that your services are running by listing the active Docker containers:

```bash
docker ps
```

You should see 3 running containers: **Zookeeper**, **Kafka**, and **Kafka UI**.

### Step 4: Access Kafka UI

Kafka UI will be accessible via a web browser at the following address:

```
http://localhost:8080
```

This is a simple interface to manage your topics, messages, and more.

### Step 5: Create a Kafka topic

#### Via Kafka UI:

1. Access Kafka UI.
2. Go to **Topics**.
3. Click on **Create a new topic**.
4. Name your topic (e.g., `my-topic`) and configure the partitions (1 by default).
5. Click on **Create**.

#### Via the command line:

You can also create a topic directly from the Kafka container.

1. Access the Kafka container:

   ```bash
   docker exec -it <kafka_container_id> /bin/bash
   ```

2. Create a topic with the following command:

   ```bash
   kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

3. Verify the topic creation:

   ```bash
   kafka-topics --list --bootstrap-server localhost:9092
   ```

### Step 6: Send a message to Kafka

#### Via Kafka UI:

1. Select the topic you just created in Kafka UI.
2. Go to the **Messages** tab.
3. Use the interface to send a message.

#### Via the command line:

1. Open a terminal on the Kafka container:

   ```bash
   docker exec -it <kafka_container_id> /bin/bash
   ```

2. Start a Kafka producer:

   ```bash
   kafka-console-producer --topic my-topic --bootstrap-server localhost:9092
   ```

3. Type a message and press Enter to send it.

### Step 7: Read messages from Kafka

#### Via Kafka UI:

1. Access Kafka UI.
2. Select the topic and go to the **Messages** tab.
3. You should see the messages sent earlier.

#### Via the command line:

1. Open a terminal on the Kafka container:

   ```bash
   docker exec -it <kafka_container_id> /bin/bash
   ```

2. Start a Kafka consumer:

   ```bash
   kafka-console-consumer --topic my-topic --from-beginning --bootstrap-server localhost:9092
   ```

3. The messages sent to the topic will be displayed in the terminal.

### Step 8: Stop Kafka and Zookeeper

When you are done, you can stop Kafka, Zookeeper, and Kafka UI with:

```bash
docker-compose down
```

This will stop and remove the containers.

### Conclusion

You now have a functional Kafka setup on Docker with Kafka UI for easy management. Here is a summary of the steps you followed:

1. Installation of Kafka and Zookeeper via Docker Compose.
2. Creation of Kafka topics.
3. Sending and reading messages via Kafka UI or the command line.

This gives you a complete environment to experiment with Kafka locally!
 

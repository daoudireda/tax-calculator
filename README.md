# Tax Calculator

## Overview
This project is a Tax Calculator application built with Java and Spring Boot. It provides endpoints to calculate taxes based on different strategies and integrates with Kafka for messaging.
I used the Strategy design pattern to implement different tax calculation strategies. The application currently supports the following strategies:
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

## Kafka Consumer
The application includes a Kafka consumer that listens for messages on the `tax-calculation-topic` topic. The consumer is implemented in the `TaxCalculatorConsumer` class.
command:
```sh
 kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic tax-calculation-topic --from-beginning
```

## Kafka Producer
The application includes a Kafka producer that sends messages to the `tax-calculation-topic` topic. The producer is implemented in the `TaxCalculatorProducer` class.
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



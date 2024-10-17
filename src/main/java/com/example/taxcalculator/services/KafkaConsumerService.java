package com.example.taxcalculator.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "tax-calculation-result", groupId = "tax-calculator")
    public void consumeTaxCalculationResult(String taxCalculationResult) {
        System.out.println("Message consommé de Kafka : " + taxCalculationResult);
    }
}

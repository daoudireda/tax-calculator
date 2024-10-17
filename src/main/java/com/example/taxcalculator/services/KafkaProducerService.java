package com.example.taxcalculator.services;

import com.example.taxcalculator.models.TaxData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaxCalculationResult(TaxData taxData, double taxAmount) {
        String message = "Revenu : " + taxData.getIncome() + ", Impôt calculé : " + taxAmount;
        kafkaTemplate.send("tax-calculation-topic", message);
    }

}

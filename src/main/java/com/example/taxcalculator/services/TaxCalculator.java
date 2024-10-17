package com.example.taxcalculator.services;

import com.example.taxcalculator.models.TaxData;
import com.example.taxcalculator.strategies.ProgressiveTaxStrategy;
import com.example.taxcalculator.strategies.SimpleTaxStrategy;
import com.example.taxcalculator.strategies.TaxStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculator {

    private TaxStrategy taxStrategy;
    private KafkaProducerService kafkaProducerService;

    public TaxCalculator(KafkaProducerService kafkaProducerService, @Qualifier("progressiveTaxStrategy")TaxStrategy taxStrategy) {
        this.kafkaProducerService = kafkaProducerService;
        this.taxStrategy = taxStrategy;
    }

    public double calculateTax(TaxData taxData) {
        double tax = taxStrategy.calculateTax(taxData.getIncome());
        kafkaProducerService.sendTaxCalculationResult(taxData, tax);
        return tax;


    }

    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    // Méthode pour créer la stratégie à partir du type
    public TaxStrategy createStrategyFromType(String strategyType) {
        return switch (strategyType.toLowerCase()) {
            case "progressive" -> new ProgressiveTaxStrategy(); // Implémentez cette stratégie
            case "simple" -> new SimpleTaxStrategy(); // Implémentez cette stratégie
            default -> throw new IllegalArgumentException("Type de stratégie inconnu : " + strategyType);
        };
    }
}

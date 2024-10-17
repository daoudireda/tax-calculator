package com.example.taxcalculator.controllers;

import com.example.taxcalculator.models.TaxData;
import com.example.taxcalculator.services.KafkaProducerService;
import com.example.taxcalculator.services.TaxCalculator;
import com.example.taxcalculator.strategies.TaxStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tax")
public class TaxController {

    private final TaxCalculator taxCalculatorService;
    private final KafkaProducerService kafkaProducerService;

    public TaxController(TaxCalculator taxCalculatorService, KafkaProducerService kafkaProducerService) {
        this.taxCalculatorService = taxCalculatorService;
        this.kafkaProducerService = kafkaProducerService;
    }

    // Endpoint pour calculer l'impôt
    @PostMapping("/calculate")
    public ResponseEntity<String> calculateTax(@RequestBody TaxData taxData) {
        try {
            // Calculer l'impôt en utilisant le service
            double taxAmount = taxCalculatorService.calculateTax(taxData);

            // Envoyer les résultats dans un topic Kafka
            kafkaProducerService.sendTaxCalculationResult(taxData, taxAmount);

            // Retourner une réponse avec les résultats du calcul
            return ResponseEntity.ok("Impôt calculé : " + taxAmount + " €");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors du calcul de l'impôt : " + e.getMessage());
        }
    }

    // Endpoint pour changer la stratégie de calcul d'impôt
    @PostMapping("/setStrategy")
    public ResponseEntity<String> setTaxStrategy(@RequestParam String strategyType) {
        try {
            // Logique pour changer la stratégie de calcul d'impôt
            TaxStrategy newStrategy = taxCalculatorService.createStrategyFromType(strategyType);
            taxCalculatorService.setTaxStrategy(newStrategy);

            return ResponseEntity.ok("Stratégie de calcul changée à : " + strategyType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors du changement de stratégie : " + e.getMessage());
        }
    }

    // Endpoint pour récupérer les messages Kafka (facultatif, si vous avez un consommateur Kafka)
    @GetMapping("/results")
    public ResponseEntity<String> getTaxCalculationResults() {
        return ResponseEntity.ok("Les résultats du calcul d'impôt seront disponibles ici.");
    }
}

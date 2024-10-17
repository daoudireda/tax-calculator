package com.example.taxcalculator.strategies;

import org.springframework.stereotype.Component;

@Component
public class SimpleTaxStrategy implements TaxStrategy {

    @Override
    public double calculateTax(double income) {
        double tax;

        // Exemple de tranches d'imposition
        if (income <= 10000) {
            tax = 0; // Pas d'impôt
        } else if (income <= 25000) {
            tax = (income - 10000) * 0.10; // 10% pour la tranche 10,001 - 25,000
        } else if (income <= 75000) {
            tax = (25000 - 10000) * 0.10 + (income - 25000) * 0.20; // 20% pour la tranche 25,001 - 75,000
        } else {
            tax = (25000 - 10000) * 0.10 + (75000 - 25000) * 0.20 + (income - 75000) * 0.30; // 30% au-delà de 75,000
        }

        return tax;
    }
}

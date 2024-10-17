package com.example.taxcalculator.strategies;

import org.springframework.stereotype.Component;

@Component
public class ProgressiveTaxStrategy implements TaxStrategy {

    @Override
    public double calculateTax(double income) {
        double tax = 0;
        if (income <= 15000) {
            tax = income * 0.05; // 5% pour la tranche jusqu'à 15,000
        } else if (income <= 40000) {
            tax = (15000 * 0.05) + (income - 15000) * 0.15; // 15% pour la tranche 15,001 - 40,000
        } else {
            tax = (15000 * 0.05) + (40000 - 15000) * 0.15 + (income - 40000) * 0.25; // 25% au-delà de 40,000
        }
        return tax;
    }
}

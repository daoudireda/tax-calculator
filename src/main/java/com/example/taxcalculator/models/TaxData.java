package com.example.taxcalculator.models;

public class TaxData {
    private double income;  // Revenu annuel

    // Constructeurs, getters et setters

    public TaxData() {}

    public TaxData(double income) {
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}

package com.example.taxcalculator.config;

import com.example.taxcalculator.strategies.ProgressiveTaxStrategy;
import com.example.taxcalculator.strategies.SimpleTaxStrategy;
import com.example.taxcalculator.strategies.TaxStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxStrategyConfig {
    @Bean
    public TaxStrategy progressiveTaxStrategy() {
        return new ProgressiveTaxStrategy();
    }

    @Bean
    public TaxStrategy simpleTaxStrategy() {
        return new SimpleTaxStrategy();
    }
}

package com.thejas.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thejas.producer.service.StockPriceProducerService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class StockPriceFetcherService {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceFetcherService.class);
    private final List<String> stockSymbols = Arrays.asList("AAPLE", "GOOGLE", "MICROSOFT", "AMAZON", "TESLA");
    private final Random random = new Random();

    @Autowired
    private StockPriceProducerService producerService;

    @Scheduled(fixedRate = 5000) // Fetch and send every 5 seconds
    public void simulateStockPrices() {
        for (String symbol : stockSymbols) {
            try {
                // Simulate realistic price ranges for each stock
                double basePrice = getBasePrice(symbol);
                double variation = basePrice * 0.02; // 2% variation
                double price = basePrice + (random.nextDouble() * variation * 2) - variation;
                
                logger.info("Simulated price for {}: {}", symbol, price);
                producerService.sendStockPrice(symbol, price);
            } catch (Exception e) {
                logger.error("Error simulating stock price for {}: {}", symbol, e.getMessage());
            }
        }
    }

    private double getBasePrice(String symbol) {
        // Return realistic base prices for each stock
        switch (symbol) {
            case "AAPL": return 170.0;
            case "GOOGL": return 2800.0;
            case "MSFT": return 350.0;
            case "AMZN": return 3300.0;
            case "TSLA": return 900.0;
            default: return 100.0;
        }
    }
}

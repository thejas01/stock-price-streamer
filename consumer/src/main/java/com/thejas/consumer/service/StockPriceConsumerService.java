package com.thejas.consumer.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.thejas.consumer.dto.StockPrice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockPriceConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceConsumerService.class);

    // Store the latest stock prices in memory
    private final Map<String, StockPrice> latestStockPrices = new ConcurrentHashMap<>();

    @KafkaListener(topics = "stock-prices", groupId = "stock-price-consumers")
    public void consume(StockPrice stockPrice) {
        logger.info("Received message: {}", stockPrice);
        latestStockPrices.put(stockPrice.getSymbol(), stockPrice);
    }

    public Map<String, StockPrice> getLatestStockPrices() {
        return latestStockPrices;
    }
}
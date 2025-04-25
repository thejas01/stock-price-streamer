package com.thejas.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.thejas.consumer.dto.StockPrice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockPriceConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceConsumerService.class);
    private final Map<String, StockPrice> latestStockPrices = new ConcurrentHashMap<>();
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "stock-prices", groupId = "stock-price-consumers")
    public void consume(StockPrice stockPrice) {
        logger.info("Received message: {}", stockPrice);
        
        // Update latest prices
        latestStockPrices.put(stockPrice.getSymbol(), stockPrice);
        
        // Broadcast to all connected clients
        messagingTemplate.convertAndSend("/topic/stock-prices", 
            Map.of("stockPrices", latestStockPrices));
    }

    public Map<String, StockPrice> getLatestStockPrices() {
        return latestStockPrices;
    }
}

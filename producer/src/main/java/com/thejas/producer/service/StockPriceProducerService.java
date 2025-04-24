package com.thejas.producer.service;

import com.thejas.producer.dto.StockPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StockPriceProducerService {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceProducerService.class);
    private static final String TOPIC = "stock-prices";

    @Autowired
    private KafkaTemplate<String, StockPrice> kafkaTemplate;

    public void sendStockPrice(String symbol, Double price) {
        StockPrice stockPrice = new StockPrice(symbol, price, LocalDateTime.now());
        logger.info("Sending message to topic {}: {}", TOPIC, stockPrice);
        kafkaTemplate.send(TOPIC, stockPrice);
    }
}
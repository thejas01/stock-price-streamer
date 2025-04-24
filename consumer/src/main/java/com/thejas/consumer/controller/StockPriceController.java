package com.thejas.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thejas.consumer.dto.StockPrice;
import com.thejas.consumer.service.StockPriceConsumerService;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})  // Updated CORS configuration
public class StockPriceController {

    @Autowired
    private StockPriceConsumerService consumerService;

    @GetMapping("/")
    public Map<String, Object> getStockPrices() {
        Map<String, StockPrice> latestPrices = consumerService.getLatestStockPrices();
        return Map.of("stockPrices", latestPrices);
    }
}

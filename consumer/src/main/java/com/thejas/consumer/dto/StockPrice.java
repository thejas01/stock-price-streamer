package com.thejas.consumer.dto;


import java.time.LocalDateTime;

public class StockPrice {
    private String symbol;
    private Double price;
    private LocalDateTime timestamp;

    // Default constructor (required for Jackson serialization)
    public StockPrice() {
    }

    public StockPrice(String symbol, Double price, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StockPrice{" +
               "symbol='" + symbol + '\'' +
               ", price=" + price +
               ", timestamp=" + timestamp +
               '}';
    }
}
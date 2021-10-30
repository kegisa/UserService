package com.victorlevin.StockService.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "stocks")
public class Stock {
    @Id
    private String id;
    private String ticker;
    private String figi;
    private Currency currency;
    private String name;
    private Type type;

    public Stock(String ticker, String figi, Currency currency, String name, Type type) {
        this.ticker = ticker;
        this.figi = figi;
        this.currency = currency;
        this.name = name;
        this.type = type;
    }
}

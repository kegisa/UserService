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
    private String currency;
    private String name;
    private String type;

    public Stock(String ticker, String figi, String currency, String name, String type) {
        this.ticker = ticker;
        this.figi = figi;
        this.currency = currency;
        this.name = name;
        this.type = type;
    }
}

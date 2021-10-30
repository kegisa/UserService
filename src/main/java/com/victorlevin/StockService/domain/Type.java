package com.victorlevin.StockService.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("Etf")
    ETF("Etf"),

    @JsonProperty("Stock")
    STOCK("Stock"),

    @JsonProperty("Bond")
    BOND("Bond");

   private String value;

    Type(String value) {
        this.value = value;
    }
}

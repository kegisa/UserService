package com.victorlevin.StockService.config;

import lombok.Data;

@Data
public class CurrencyConfig {
    private String currencyService;
    private String getRate;
}

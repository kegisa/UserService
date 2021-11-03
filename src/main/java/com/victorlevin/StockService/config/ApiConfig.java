package com.victorlevin.StockService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private TinkoffConfig tinkoffConfig;
    private PriceConfig priceServiceConfig;
    private CurrencyConfig currencyConfig;
}

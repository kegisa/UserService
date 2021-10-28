package com.victorlevin.StockService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private String priceService;
    private String getPricesByTickers;
}

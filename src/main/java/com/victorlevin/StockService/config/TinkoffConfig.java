package com.victorlevin.StockService.config;

import lombok.Data;

@Data
public class TinkoffConfig {
    private String tinkoffService;
    private String getPriceByTicker;
    private String getPricesByTickers;
}

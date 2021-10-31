package com.victorlevin.StockService.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StockCreateDto {
    private String ticker;
    private String name;
    private String type;
    private String figi;
    private String currency;
}

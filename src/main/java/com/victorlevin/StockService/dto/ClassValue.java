package com.victorlevin.StockService.dto;

import com.victorlevin.StockService.domain.Type;
import lombok.Value;

@Value
public class ClassValue {
    private Type classActive;
    private Double value;
}

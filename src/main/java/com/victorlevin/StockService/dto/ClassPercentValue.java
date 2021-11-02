package com.victorlevin.StockService.dto;

import com.victorlevin.StockService.domain.Type;
import lombok.Value;

@Value
public class ClassPercentValue {
    private Type classActive;
    private Integer value;
}

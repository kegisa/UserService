package com.victorlevin.StockService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private String ticker;
    private Integer quantity;
}

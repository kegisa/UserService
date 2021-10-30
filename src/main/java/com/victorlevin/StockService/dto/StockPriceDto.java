package com.victorlevin.StockService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class StockPriceDto {
    private String figi;
    private Double price;
}

package com.victorlevin.StockService.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class GetPricesDto {
    private List<String> figies;
}

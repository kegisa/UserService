package com.victorlevin.StockService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StockPricesDto {
    private List<StockPriceDto> prices;
}

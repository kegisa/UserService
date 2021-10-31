package com.victorlevin.StockService.dto;

import com.victorlevin.StockService.domain.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StocksDto {
    List<Stock> stocks;
}

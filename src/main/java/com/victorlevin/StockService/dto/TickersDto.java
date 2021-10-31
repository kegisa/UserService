package com.victorlevin.StockService.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TickersDto {
    private Set<String> tickers;
}

package com.victorlevin.StockService.controller;

import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.dto.StockCreateDTO;
import com.victorlevin.StockService.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/{ticker}")
    public Stock getStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        return stock;
    }

    @PostMapping
    public StockCreateDTO createStock(@RequestBody StockCreateDTO stockDto) {
        stockService.createStock(stockDto);
        return stockDto;
    }
}

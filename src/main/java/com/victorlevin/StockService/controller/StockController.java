package com.victorlevin.StockService.controller;

import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.dto.StockCreateDto;
import com.victorlevin.StockService.dto.TickersDto;
import com.victorlevin.StockService.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @PutMapping("/{ticker}")
    public Stock addStockFromTinkoff(@PathVariable String ticker) {
        return stockService.addStockFromTinkoff(ticker);
    }

    @PostMapping("addByTickersFromTinkoff")
    public List<Stock> addStocksFromTinkoff(@RequestBody TickersDto tickers) {
        return stockService.addStocksFromTinkoff(tickers);
    }

    @PostMapping
    public StockCreateDto createStock(@RequestBody StockCreateDto stockDto) {
        stockService.createStock(stockDto);
        return stockDto;
    }

    @DeleteMapping("/{ticker}")
    public void deleteStockByTicker(@PathVariable String ticker) {
        stockService.deleteStockByTicker(ticker);
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        log.info("Getting all stocks");
        return stockService.getAllStocks();
    }
}

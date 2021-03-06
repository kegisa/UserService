package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Currency;
import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.domain.Type;
import com.victorlevin.StockService.dto.StockCreateDto;
import com.victorlevin.StockService.dto.StocksDto;
import com.victorlevin.StockService.dto.TickersDto;
import com.victorlevin.StockService.exception.StockAlreadyExistException;
import com.victorlevin.StockService.exception.StockNotFoundException;
import com.victorlevin.StockService.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public Stock getStockByTicker(String ticker) {
        return stockRepository.findByTicker(ticker).orElseThrow(() -> new StockNotFoundException("Stock not found. Try another ticker."));
    }

    public void deleteStockByTicker(String ticker) {
        stockRepository.deleteStockByTicker(ticker).orElseThrow(() -> new StockNotFoundException("Stock not found."));
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<Stock> getStocksByTickers(List<String> tickers) {
        return stockRepository.findByTickerIn(tickers);
    }
}

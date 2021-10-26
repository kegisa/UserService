package com.victorlevin.StockService.repository;

import com.victorlevin.StockService.domain.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {
    Optional<Stock> findByTicker(String ticker);

    boolean existsByTicker(String ticker);

    List<Stock> findByTickerIn(List<String> tickers);

    Optional<Stock> deleteStockByTicker(String ticker);
}

package com.victorlevin.StockService.repository;

import com.victorlevin.StockService.domain.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {
    Stock findByTicker(String ticker);
    boolean existsByTicker(String ticker);
}

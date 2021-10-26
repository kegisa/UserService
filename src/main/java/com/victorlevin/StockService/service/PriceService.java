package com.victorlevin.StockService.service;

import org.springframework.stereotype.Service;

@Service
public class PriceService {
    public Double getPriceByTicker(String ticker) {
        if(ticker.equals("SBSP")) {
            return 10.0;
        } else if (ticker.equals("FXCN")) {
            return 50.0;
        } else {
            return 100.0;
        }
    }
}

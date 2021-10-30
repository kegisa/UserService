package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Currency;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    public Double exchangeRate(Currency currency) {
        if(currency == Currency.RUB) {
            return 1.0;
        } else if(currency == Currency.USD) {
            return 71.0;
        } else {
            return 5.0;
        }
    }
}

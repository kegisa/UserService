package com.victorlevin.StockService.exception;

public class CouldntGetPricesException extends RuntimeException {
    public CouldntGetPricesException(String s) {
        super(s);
    }
}

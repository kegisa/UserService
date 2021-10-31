package com.victorlevin.StockService.service;

import com.victorlevin.StockService.config.ApiConfig;
import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.dto.StocksDto;
import com.victorlevin.StockService.dto.TickersDto;
import com.victorlevin.StockService.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Slf4j
@Service
public class TinkoffService {
    private final ApiConfig config;

    private final RestTemplate restTemplate;

    public TinkoffService(RestTemplateBuilder restTemplateBuilder, ApiConfig config) {
        this.config = config;
        this.restTemplate = restTemplateBuilder.build();
    }


    public Stock getStockByTicker(String ticker) {
        String url = config.getTinkoffConfig().getTinkoffService() + config.getTinkoffConfig().getGetPriceByTicker();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        log.info("Getting {} from TinkoffService", ticker);
        ResponseEntity<Stock> responseEntity
                = this.restTemplate.getForEntity(url, Stock.class, ticker);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new StockNotFoundException("Stock not found in Tinkoff. Try another ticker.");
        }
    }

    public StocksDto getStocksByTickers(TickersDto tickers) {
        String url = config.getTinkoffConfig().getTinkoffService() + config.getTinkoffConfig().getGetPricesByTickers();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        log.info("Getting {} from TinkoffService", tickers.getTickers());
        ResponseEntity<StocksDto> responseEntity
                = this.restTemplate.postForEntity(url, tickers, StocksDto.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new StockNotFoundException("Stocks not found in Tinkoff. Try another tickers.");
        }
    }
}

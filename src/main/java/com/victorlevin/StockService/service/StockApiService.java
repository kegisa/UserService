package com.victorlevin.StockService.service;

import com.victorlevin.StockService.config.ApiConfig;
import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.dto.StocksDto;
import com.victorlevin.StockService.dto.TickersDto;
import com.victorlevin.StockService.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
public class StockApiService {
    private final ApiConfig config;
    private final RestTemplate restTemplate;

    public StockApiService(RestTemplateBuilder restTemplateBuilder, ApiConfig config) {
        this.config = config;
        this.restTemplate = restTemplateBuilder.build();
    }

    public StocksDto getStocksByTickers(TickersDto tickers) {
        String url = config.getStockConfig().getStockService() + config.getStockConfig().getGetStocksByTickers();
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

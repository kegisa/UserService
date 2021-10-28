package com.victorlevin.StockService.service;

import com.victorlevin.StockService.config.ApiConfig;
import com.victorlevin.StockService.dto.GetPricesDto;
import com.victorlevin.StockService.dto.StockPricesDto;
import com.victorlevin.StockService.exception.PriceServiceException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PriceService {
    private final ApiConfig config;

    private final RestTemplate restTemplate;

    public PriceService(RestTemplateBuilder restTemplateBuilder, ApiConfig config) {
        this.config = config;
        this.restTemplate = restTemplateBuilder.build();
    }


    public Map<String, Double> getPricesByTicker(GetPricesDto dto) {
        Map<String, Double> prices = new HashMap<>();
        String url = config.getPriceService() + config.getGetPricesByTickers();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<GetPricesDto> entity = new HttpEntity<>(dto, headers);
        ResponseEntity<StockPricesDto> responseEntity
                = this.restTemplate.postForEntity(url, entity, StockPricesDto.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            responseEntity.getBody().getPrices().forEach(i -> prices.put(i.getTicker(), i.getPrice()));
            return prices;
        } else {
            throw new PriceServiceException(responseEntity.toString());
        }
    }
}

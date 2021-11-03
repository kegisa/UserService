package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.*;
import com.victorlevin.StockService.dto.ClassValue;
import com.victorlevin.StockService.dto.*;
import com.victorlevin.StockService.exception.CouldntGetPricesException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserService userService;
    private final StockService stockService;
    private final PriceService priceService;
    private final CurrencyService currencyService;

    public ClassesPercentDto getStatisticOfClassesByUserId(String userId) {
        long start = System.currentTimeMillis();
        User user = userService.getUserById(userId);
        Map<String, Integer> tickersWithQuantity = getTickersWithQuantity(user);
        List<Stock> stocksInPortfolio = getStocksUser(user);

        if(stocksInPortfolio.isEmpty()) {
            throw new CouldntGetPricesException("User hasn't any stocks.");
        }

        Map<String, Double> figiesWithPrices = getPricesStocks(stocksInPortfolio);
        Map<Currency, Double> currencyRates = getRates(stocksInPortfolio);

        Map<Type, Double> classesWithCost = new HashMap<>();
        stocksInPortfolio.forEach(stock -> {
            Double cost = figiesWithPrices.get(stock.getFigi()) * tickersWithQuantity.get(stock.getTicker()) * currencyRates.get(stock.getCurrency());
            if(!classesWithCost.containsKey(stock.getType())) {
                classesWithCost.put(stock.getType(), cost);
            } else {
                Double newCost = classesWithCost.get(stock.getType()) + cost;
                classesWithCost.put(stock.getType(), newCost);
            }
        });

        Double result = classesWithCost.values().stream().mapToDouble(Double::doubleValue).sum();

        List<ClassPercentValue> classValues = new ArrayList<>();
        classesWithCost.forEach((k,v) -> classValues.add(new ClassPercentValue(k, (int) Math.round(100 *(v / result)))));
        log.info("Calculate time for classes stat - {}", System.currentTimeMillis() - start);
        return new ClassesPercentDto(userId, classValues);
    }


    public CostDto getCostPortfolio(String userId) {
        long start = System.currentTimeMillis();
        User user = userService.getUserById(userId);
        Map<String, Integer> tickersWithQuantity = getTickersWithQuantity(user);
        List<Stock> stocksInPortfolio = getStocksUser(user);

        if (stocksInPortfolio.isEmpty()) {
            throw new CouldntGetPricesException("User hasn't any stocks.");
        }

        Map<String, Double> figiesWithPrices = getPricesStocks(stocksInPortfolio);
        Map<Currency, Double> currencyRates = getRates(stocksInPortfolio);


        Double resultCost = getCostByStocks(stocksInPortfolio, figiesWithPrices, tickersWithQuantity, currencyRates);

        log.info("Calculate time for cost - {}", System.currentTimeMillis() - start);
        return new CostDto(resultCost);
    }

    public ClassValue getStatisticOfClassByUserId(String userId, String typeStr) {
        Type type = Type.valueOf(typeStr.toUpperCase());
        User user = userService.getUserById(userId);
        Map<String, Integer> tickersWithQuantity = getTickersWithQuantity(user);
        List<Stock> stocksInPortfolio = getStocksUser(user);

        stocksInPortfolio = stocksInPortfolio.stream().filter(s -> s.getType() == type).collect(Collectors.toList());

        if (stocksInPortfolio.isEmpty()) {
            throw new CouldntGetPricesException(String.format("User hasn't stocks with type ", type));
        }

        Map<String, Double> figiesWithPrices = getPricesStocks(stocksInPortfolio);
        Map<Currency, Double> currencyRates = getRates(stocksInPortfolio);


        Double resultCost = getCostByStocks(stocksInPortfolio, figiesWithPrices, tickersWithQuantity, currencyRates);
        return new ClassValue(type, resultCost);
    }

    private Map<String, Integer> getTickersWithQuantity(User user) {
        return user.getPortfolio().stream().collect(Collectors.toMap(Position::getTicker, Position::getQuantity));
    }

    private List<Stock> getStocksUser(User user) {
        return stockService.getStocksByTickers(
                user.getPortfolio().stream()
                        .map(s -> s.getTicker())
                        .collect(Collectors.toList()));
    }

    private Map<String, Double> getPricesStocks(List<Stock> stocks) {
        return priceService.getPricesByFigies(
                new GetPricesDto(
                        stocks.stream()
                                .map(stock -> stock.getFigi())
                                .collect(Collectors.toList())));

    }

    private Map<Currency, Double> getRates(List<Stock> stocksInPortfolio) {
        return currencyService.getRates(
                stocksInPortfolio.stream()
                        .map(s -> s.getCurrency())
                        .distinct().collect(Collectors.toList()));
    }

    private Double getCostByStocks(List<Stock> stocksInPortfolio, Map<String, Double> figiesWithPrices,
                                   Map<String, Integer> tickersWithQuantity, Map<Currency, Double> currencyRates) {
        return stocksInPortfolio.stream()
                .map(s -> figiesWithPrices.get(s.getFigi())
                        * tickersWithQuantity.get(s.getTicker())
                        * currencyRates.get(s.getCurrency()))
                .mapToDouble(Double::doubleValue).sum();
    }
}

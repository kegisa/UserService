package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Position;
import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.domain.Type;
import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.ClassDto;
import com.victorlevin.StockService.dto.ClassValue;
import com.victorlevin.StockService.dto.CostDto;
import com.victorlevin.StockService.dto.GetPricesDto;
import com.victorlevin.StockService.exception.CouldntGetPricesException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserService userService;
    private final StockService stockService;
    private final PriceService priceService;
    private final CurrencyService currencyService;

    public ClassDto getStatisticOfClassesByUserId(String userId) {
        long start = System.currentTimeMillis();
        User user = userService.getUserById(userId);
        Map<String, Integer> tickersWithQuantity = user.getPortfolio().stream().collect(Collectors.toMap(Position::getTicker, Position::getQuantity));
        List<Stock> stocksInPortfolio = stockService.getStocksByTickers(
                user.getPortfolio().stream()
                        .map(s -> s.getTicker())
                        .collect(Collectors.toList()));

        if(stocksInPortfolio.isEmpty()) {
            throw new CouldntGetPricesException("User hasn't any stocks.");
        }

        Map<String, Double> figiesWithPrices = priceService.getPricesByFigies(
                new GetPricesDto(
                        stocksInPortfolio.stream()
                                .map(stock -> stock.getFigi())
                                .collect(Collectors.toList())));

        HashMap<Type, Double> classesWithCost = new HashMap<>();
        stocksInPortfolio.forEach(stock -> {
            Double cost = figiesWithPrices.get(stock.getFigi()) * tickersWithQuantity.get(stock.getTicker()) * currencyService.exchangeRate(stock.getCurrency());
            if(!classesWithCost.containsKey(stock.getType())) {
                classesWithCost.put(stock.getType(), cost);
            } else {
                Double newCost = classesWithCost.get(stock.getType()) + cost;
                classesWithCost.put(stock.getType(), newCost);
            }
        });

        Double result = classesWithCost.values().stream().mapToDouble(Double::doubleValue).sum();

        List<ClassValue> classValues = new ArrayList<>();
        classesWithCost.forEach((k,v) -> classValues.add(new ClassValue(k, v / result)));
        log.info("Calculate time for classes stat - {}", System.currentTimeMillis() - start);
        return new ClassDto(userId, classValues);
    }

    public CostDto getCostPortfoio(String userId) {
        long start = System.currentTimeMillis();
        User user = userService.getUserById(userId);
        Map<String, Integer> tickersWithQuantity = user.getPortfolio().stream().collect(Collectors.toMap(Position::getTicker, Position::getQuantity));
        List<Stock> stocksInPortfolio = stockService.getStocksByTickers(
                user.getPortfolio().stream()
                        .map(s -> s.getTicker())
                        .collect(Collectors.toList()));

        if (stocksInPortfolio.isEmpty()) {
            throw new CouldntGetPricesException("User hasn't any stocks.");
        }

        Map<String, Double> figiesWithPrices = priceService.getPricesByFigies(
                new GetPricesDto(
                        stocksInPortfolio.stream()
                                .map(stock -> stock.getFigi())
                                .collect(Collectors.toList())));

        Double resultCost = stocksInPortfolio.stream()
                .map(s -> figiesWithPrices.get(s.getFigi())
                        * tickersWithQuantity.get(s.getTicker())
                        * currencyService.exchangeRate(s.getCurrency()))
                .mapToDouble(Double::doubleValue).sum();

        log.info("Calculate time for cost - {}", System.currentTimeMillis() - start);
        return new CostDto(resultCost);
    }
}

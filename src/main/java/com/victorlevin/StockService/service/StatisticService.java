package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Position;
import com.victorlevin.StockService.domain.Stock;
import com.victorlevin.StockService.domain.Type;
import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.ClassDto;
import com.victorlevin.StockService.dto.ClassValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UserService userService;
    private final StockService stockService;
    private final PriceService priceService;

    public ClassDto getStatisticOfClassesByUserId(String userId) {
        User user  = userService.getUserById(userId);
        List<Position> positions  = user.getPortfolio();
        Map<String, Stock> stocks = getMapStocksByTickers(positions.stream()
                .map(Position::getTicker)
                .collect(Collectors.toList()));

        Map<Type, Double> valuesClasses = new HashMap<>();

        positions.stream().forEach(position -> {
            Double cost = getCostByTicker(position.getTicker(), position.getQuantity());
            Type type = stocks.get(position.getTicker()).getType();
            if(valuesClasses.containsKey(type)) {
                Double resultCost = valuesClasses.get(type) + cost;
                valuesClasses.put(type, resultCost);
            } else {
                valuesClasses.put(type, cost);
            }
        });

        Double sum = valuesClasses.values().stream().mapToDouble(Double::doubleValue).sum();

        List<ClassValue> values = new ArrayList<>();
        valuesClasses.forEach((k,v) -> values.add(new ClassValue(k,v/sum)));
        return new ClassDto(userId, values);
    }

    private Map<String, Stock> getMapStocksByTickers(List<String> tickers) {
        return stockService.getStocksByTickers(tickers).stream()
                .collect(Collectors.toMap(Stock::getTicker, i -> i));
    }

    private Double getCostByTicker(String ticker, Integer quantity) {
        return priceService.getPriceByTicker(ticker)*quantity;
    }
}

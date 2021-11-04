package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Position;
import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.PositionsDto;
import com.victorlevin.StockService.dto.StocksDto;
import com.victorlevin.StockService.dto.TickersDto;
import com.victorlevin.StockService.dto.UserDtoCreate;
import com.victorlevin.StockService.exception.UserAlreadyExistException;
import com.victorlevin.StockService.exception.UserNotFoundException;
import com.victorlevin.StockService.repository.StockRepository;
import com.victorlevin.StockService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final TinkoffService tinkoffService;

    public User createUser(UserDtoCreate userDtoCreate) {
        if(userRepository.existsById(userDtoCreate.getId())) {
            throw new UserAlreadyExistException("User already exist. Try another Id.");
        }

        Set<String> notExistTickers = new HashSet<>();
        for(Position position : userDtoCreate.getPortfolio()) {
            if (!stockRepository.existsByTicker(position.getTicker())) {
                notExistTickers.add(position.getTicker());
            }
        }

        StocksDto stocksDto = tinkoffService.getStocksByTickers(new TickersDto(notExistTickers));
        stockRepository.saveAll(stocksDto.getStocks());

        return userRepository.save(new User(
                userDtoCreate.getId(),
                userDtoCreate.getName(),
                userDtoCreate.getPortfolio()
        ));
    }

    public User addStocksToUser(String id, PositionsDto positionsDto) {
        Set<Position> addPositions = new HashSet<>();
        addPositions.addAll(positionsDto.getPositions());

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Set<Position> userPositions = user.getPortfolio();
        addPositions.removeAll(userPositions);
        if(!addPositions.isEmpty()) {
            TickersDto tickersDto = new TickersDto(
                    addPositions.stream().map(p -> p.getTicker()).collect(Collectors.toSet()));

            StocksDto stocksDto = tinkoffService.getStocksByTickers(tickersDto);
            stockRepository.saveAll(stocksDto.getStocks());
        }

        positionsDto.getPositions().retainAll(userPositions);
        userPositions.removeAll(positionsDto.getPositions());
        userPositions.addAll(positionsDto.getPositions());
        if(!addPositions.isEmpty()) {
            userPositions.addAll(addPositions);
        }

        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found. Try another Id."));
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}

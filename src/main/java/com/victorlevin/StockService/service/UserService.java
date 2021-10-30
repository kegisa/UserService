package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.Position;
import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.UserDtoCreate;
import com.victorlevin.StockService.exception.StockNotFoundException;
import com.victorlevin.StockService.exception.UserAlreadyExistException;
import com.victorlevin.StockService.exception.UserNotFoundException;
import com.victorlevin.StockService.repository.StockRepository;
import com.victorlevin.StockService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    public User createUser(UserDtoCreate userDtoCreate) {
        if(userRepository.existsById(userDtoCreate.getId())) {
            throw new UserAlreadyExistException("User already exist. Try another Id.");
        }

        for(Position position : userDtoCreate.getPortfolio()) {
            if (!stockRepository.existsByTicker(position.getTicker())) {
                throw new StockNotFoundException(String.format("Stock with ticker %s not found.", position.getTicker()));
            }
        }

        return userRepository.save(new User(
                userDtoCreate.getId(),
                userDtoCreate.getName(),
                userDtoCreate.getPortfolio()
        ));
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found. Try another Id."));
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}

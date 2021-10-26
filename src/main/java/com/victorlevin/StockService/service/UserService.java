package com.victorlevin.StockService.service;

import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.UserDtoCreate;
import com.victorlevin.StockService.exception.UserAlreadyExistException;
import com.victorlevin.StockService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDtoCreate userDtoCreate) {
        if(userRepository.existsById(userDtoCreate.getId())) {
            throw new UserAlreadyExistException("User already exist. Try another Id.");
        }

        User user = new User(
                userDtoCreate.getId(),
                userDtoCreate.getName(),
                userDtoCreate.getPortfolio());

        userRepository.save(user);
    }
}

package com.victorlevin.StockService.controller;

import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.PositionsDto;
import com.victorlevin.StockService.dto.UserDtoCreate;
import com.victorlevin.StockService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserDtoCreate userDtoCreate) {
        return userService.createUser(userDtoCreate);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/stocks")
    public User addStocksToUser(@PathVariable String id, @RequestBody PositionsDto positionsDto) {
        return userService.addStocksToUser(id, positionsDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

}

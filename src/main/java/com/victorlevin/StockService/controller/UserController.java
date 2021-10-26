package com.victorlevin.StockService.controller;

import com.victorlevin.StockService.domain.User;
import com.victorlevin.StockService.dto.UserDtoCreate;
import com.victorlevin.StockService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDtoCreate createUser(@RequestBody UserDtoCreate userDtoCreate) {
        userService.createUser(userDtoCreate);
        return userDtoCreate;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
}

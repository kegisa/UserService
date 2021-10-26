package com.victorlevin.StockService.repository;

import com.victorlevin.StockService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

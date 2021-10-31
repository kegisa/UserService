package com.victorlevin.StockService.dto;

import com.victorlevin.StockService.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@AllArgsConstructor
public class UserDtoCreate {
    private String id;
    private String name;
    private Set<Position> portfolio;
}

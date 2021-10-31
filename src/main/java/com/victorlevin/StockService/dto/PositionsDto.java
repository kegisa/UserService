package com.victorlevin.StockService.dto;

import com.victorlevin.StockService.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PositionsDto {
    private Set<Position> positions;
}

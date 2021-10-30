package com.victorlevin.StockService.controller;

import com.victorlevin.StockService.dto.ClassDto;
import com.victorlevin.StockService.dto.CostDto;
import com.victorlevin.StockService.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/statistic")
@RequiredArgsConstructor
@RestController
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/classes/{userId}")
    public ClassDto getClassStat(@PathVariable String userId) {
        return statisticService.getStatisticOfClassesByUserId(userId);
    }

    @GetMapping("/cost/{userId}")
    public CostDto getCostPortfolio(@PathVariable String userId) {
        return statisticService.getCostPortfoio(userId);
    }
}

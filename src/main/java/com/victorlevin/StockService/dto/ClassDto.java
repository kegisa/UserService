package com.victorlevin.StockService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@AllArgsConstructor
public class ClassDto {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("classes")
    private List<ClassValue> classValues;
}

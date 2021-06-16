package com.example.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockWareHouse {
    private List<Map<String,Object>> batchStock;

}

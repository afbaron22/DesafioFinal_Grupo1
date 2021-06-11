package com.mercadolibre.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchedWarehouseProducts {
    private String productId;
    private List<Map<String,String>> warehouses;
}

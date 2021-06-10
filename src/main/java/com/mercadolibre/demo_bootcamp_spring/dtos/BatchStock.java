package com.mercadolibre.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class BatchStock {
    private List<BatchResponse> batchStock;
}

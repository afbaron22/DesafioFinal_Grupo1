package com.mercadolibre.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStock {
    private List<BatchResponse> batchStock;
}

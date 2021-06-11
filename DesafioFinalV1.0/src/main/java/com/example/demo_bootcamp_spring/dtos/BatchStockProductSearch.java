package com.example.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockProductSearch {
    private SectionDTO section;
    private String productId;
    private List<BatchStockProduct> batchStock;
}

package com.example.demo2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockProduct {
    private String batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;
}

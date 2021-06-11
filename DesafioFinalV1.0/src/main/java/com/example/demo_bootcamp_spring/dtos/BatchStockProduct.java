package com.example.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockProduct {
    private String batchnumber;
    private Integer currentQuantity;
    private LocalDate dueDate;
}

package com.example.demo2.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponse {
    private String batchNumber;
    private String productId;
    private Float currentTemperature;
    private Float minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private String manufacturingDate;
    private String manufacturingTime;
    private String dueDate;

}

package com.example.demo_bootcamp_spring.dtos;

import com.example.demo_bootcamp_spring.models.State;

import java.time.LocalDate;

public class BatchDueDateResponse {
    private String batchNumber;
    private String productId;
    private State productTypeId;
    private LocalDate dueDate;
    private Integer quantity;
}

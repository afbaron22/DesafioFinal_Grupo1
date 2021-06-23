package com.example.demo_bootcamp_spring.dtos;

import com.example.demo_bootcamp_spring.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDetailDTO {
    private String productId;
    private String name;
    private String additionalInfo;
    private State state;
    private Double price;
    private Integer quantity;

}

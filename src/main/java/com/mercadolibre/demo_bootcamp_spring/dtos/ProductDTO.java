package com.mercadolibre.demo_bootcamp_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    String productId;
   Integer quantity;
}
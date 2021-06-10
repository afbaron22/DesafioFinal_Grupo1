package com.mercadolibre.demo_bootcamp_spring.dtos;

import com.mercadolibre.demo_bootcamp_spring.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    Date date;
    String buyerId;
    List<ProductDTO> products;
}

package com.example.demo_bootcamp_spring.dtos;

import com.example.demo_bootcamp_spring.models.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusDTO {
    OrderStatus statusCode;
}

package com.example.demo_bootcamp_spring.services;



import com.example.demo_bootcamp_spring.dtos.OrderDTO;
import com.example.demo_bootcamp_spring.models.OrderProduct;

import java.util.Set;

public interface IOrderService {
    Double registerOrder(OrderDTO orderDTO);

    Set<OrderProduct> getOrderDetail(Integer idOrder);

    void updateOrder(Integer idOrder, OrderDTO orderDTO);
}

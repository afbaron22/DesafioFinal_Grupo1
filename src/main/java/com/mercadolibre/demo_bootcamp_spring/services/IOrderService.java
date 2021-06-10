package com.mercadolibre.demo_bootcamp_spring.services;

import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Product;

import java.util.List;

public interface IOrderService {

    Double registerOrder(OrderDTO orderDTO);

    List<Product> getOrderDetail(Integer idOrder);

    void updateOrder(Integer idOrder, OrderDTO orderDTO);

}

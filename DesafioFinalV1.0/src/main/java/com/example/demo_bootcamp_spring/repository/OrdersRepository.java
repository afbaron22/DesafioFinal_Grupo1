package com.example.demo_bootcamp_spring.repository;


import com.example.demo_bootcamp_spring.dtos.OrderProductDetailDTO;
import com.example.demo_bootcamp_spring.models.OrderProduct;
import com.example.demo_bootcamp_spring.models.Orders;
import com.example.demo_bootcamp_spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    @Query("SELECT NEW com.example.demo_bootcamp_spring.dtos.OrderProductDetailDTO(p.productId,p.name,p.additionalInfo,p.state,p.price,o.quantity) " +
            "FROM Orders u LEFT JOIN u.orderProducts o LEFT JOIN o.product p WHERE u.orderId = :idOrder")
    Optional<List<OrderProductDetailDTO>> getProductsInOrder(@Param("idOrder") Integer idOrder);

}

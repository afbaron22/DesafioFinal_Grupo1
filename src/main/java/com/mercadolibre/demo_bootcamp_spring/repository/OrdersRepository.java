package com.mercadolibre.demo_bootcamp_spring.repository;


import com.mercadolibre.demo_bootcamp_spring.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {

}

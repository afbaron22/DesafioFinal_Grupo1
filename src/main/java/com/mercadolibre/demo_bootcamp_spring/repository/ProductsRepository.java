package com.mercadolibre.demo_bootcamp_spring.repository;

import com.mercadolibre.demo_bootcamp_spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product,Integer> {

}

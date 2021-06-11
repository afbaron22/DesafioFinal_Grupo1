package com.example.demo_bootcamp_spring.repository;

import com.example.demo_bootcamp_spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product,String> {


}

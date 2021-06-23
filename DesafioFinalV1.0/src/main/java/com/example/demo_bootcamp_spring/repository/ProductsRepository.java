package com.example.demo_bootcamp_spring.repository;

import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product,String> {

    @Query("select p from Product p where p.state = ?1")
    List<Product> findByCategory(State category);
}

package com.example.demo_bootcamp_spring.services.Product;


import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();

    List<Product> getProductsByCategory(State state);
}

package com.mercadolibre.demo_bootcamp_spring.services.Product;

import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.models.State;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();

    List<Product> getProductsByCategory(State state);
}

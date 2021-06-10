package com.mercadolibre.demo_bootcamp_spring.services.Product;

import com.mercadolibre.demo_bootcamp_spring.exceptions.ProductsNotFoundException;
import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.models.State;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService{

    public ProductsRepository productsRepo;

    public ProductService(ProductsRepository productsRepo) {
        this.productsRepo = productsRepo;
    }

    //TODO pasar de Objet a DTO para devolver al front DTO

    @Override
    public List<Product> getProducts() {
        if (productsRepo.count() <= 0){
            throw new ProductsNotFoundException("No products where found");

        }
        return productsRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(State state) {
        List<Product> productList = new ArrayList<>();
        productList = productsRepo.findByCategory(state.toString());
        if (productList == null){
            throw new ProductsNotFoundException("No products where found in the category: " + state.toString() );
        }
        return productList;
    }
}

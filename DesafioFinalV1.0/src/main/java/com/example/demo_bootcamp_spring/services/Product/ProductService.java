package com.example.demo_bootcamp_spring.services.Product;

import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductsRepository productsRepo;
    private final BatchRepository batchRepository;

    public ProductService(ProductsRepository productsRepo, BatchRepository batchRepository) {
        this.productsRepo = productsRepo;
        this.batchRepository = batchRepository;
    }

    @Override
    public List<Product> getProducts() {
        if (productsRepo.count() <= 0){
            throw new ProductsOutOfStockException("No products where found");
        }
        return productsRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(State state) {
        List<Product> list = batchRepository
            .findProduct(state)
            .orElseThrow(()-> new ProductsOutOfStockException("No products where found in the category: " + state.toString()));

        if(list.isEmpty()){
            throw new ProductsOutOfStockException("No products where found in the category: " + state.toString());
        }
        return list;
    }
}

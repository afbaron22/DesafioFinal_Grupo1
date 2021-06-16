package com.example.demo_bootcamp_spring.services.Product;

import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.services.Batch.IBatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    public ProductsRepository productsRepo;
    public IBatchService batchService;
    private BatchRepository batchRepository;

    public ProductService(ProductsRepository productsRepo, IBatchService batchService, BatchRepository batchRepository) {
        this.productsRepo = productsRepo;
        this.batchService = batchService;
        this.batchRepository = batchRepository;
    }

//TODO pasar de Objet a DTO para devolver al front DTO

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

package com.example.demo_bootcamp_spring.service;

import com.example.demo_bootcamp_spring.exceptions.ProductNotFoundException;
import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.services.Product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo_bootcamp_spring.models.State.FF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    private Product product6;
    private List<Product> productRepoMOCK;

    @Mock
    public ProductsRepository productsRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private BatchRepository batchRepository;

    @BeforeEach
    void setup(){
        product1 = new Product("1", "Bananas","Banaas Bananón", State.FS, 29.99);
        product2 = new Product("2", "Peras","Peras frescas", State.FS, 40.23);
        product3 = new Product("3", "Merluza","Merluza fresca empanada", FF, 100.75);
        product4 = new Product("4", "Calamares","Calamares congelados separados por kilo", FF, 123.99);
        product5 = new Product("5", "Leche","Leche parcialmente descremada Sancor", State.RF, 20.00);
        product6 = new Product("6", "Yogurt","Yogurt griego La Serenísima", State.RF, 25.25);

        productRepoMOCK = Arrays.asList(product1,product2,product3,product4,product5, product6);
    }

    @Test
    public void shouldGetAllProducts(){
        List<Product> productsList;
        when(productsRepository.count()).thenReturn((long)productRepoMOCK.size());
        when(productsRepository.findAll()).thenReturn(productRepoMOCK);
        productsList = productService.getProducts();
        assertEquals(productRepoMOCK,productsList);
    }

    @Test
    public void shouldThrowProductNotFoundExceptionWhenGetAllProducts(){
        when(productsRepository.count()).thenReturn((long)0);
        when(productsRepository.findAll()).thenReturn(productRepoMOCK);
        assertThrows(ProductNotFoundException.class,() -> productService.getProducts());
    }

    @Test
    public void shouldGetProductsByCategory (){
        List<Product> freshProducts = Arrays.asList(product1,product2);
        when(batchRepository.findProduct(State.FS)).thenReturn(Optional.of(freshProducts));
        List<Product> productsList = productService.getProductsByCategory(State.FS);
        assertEquals(freshProducts,productsList);
    }

    @Test
    public void shouldThrowProductsOutOfStockException(){
        when(batchRepository.findProduct(State.FS)).thenReturn(Optional.empty());
        assertThrows(ProductsOutOfStockException.class,() ->  productService.getProductsByCategory(State.FS));
    }

}

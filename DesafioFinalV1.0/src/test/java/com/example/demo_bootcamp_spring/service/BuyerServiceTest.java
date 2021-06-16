package com.example.demo_bootcamp_spring.service;

import com.example.demo_bootcamp_spring.dtos.InboundOrderTransaction;
import com.example.demo_bootcamp_spring.exceptions.NoRelatedWarehousesToProduct;
import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.InboundOrderRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.repository.SectionRepository;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.example.demo_bootcamp_spring.services.Product.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BuyerServiceTest {
    @Mock
    BatchRepository batchRepository;
    @Mock
    SectionRepository sectionRepository;
    @Mock
    ProductsRepository productsRepository;

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @InjectMocks
    BatchService batchService;
    @InjectMocks
    ProductService productService;


    private InboundOrderTransaction inboundOrderTransaction;
    //------------------------------------------TESTS METHOD GETPRODUCTSBYCATEGORY--------------------------------------------------


    @Test
    void testGetProductsByCategory() {
        when(batchRepository.findProduct(State.FF)).thenReturn(java.util.Optional.of(createProductList()));
        List<Product> productList = productService.getProductsByCategory(State.FF);
        assertEquals("1", productList.get(0).getProductId());
        assertEquals("2", productList.get(1).getProductId());
        assertEquals("product1", productList.get(0).getName());
        assertEquals("product2", productList.get(1).getName());
        assertEquals(State.FF, productList.get(0).getState());
        assertEquals(State.FF, productList.get(1).getState());
    }

    @Test
    void testGetProductsByCategoryException() {

        when(batchRepository.findProduct(State.FF)).thenThrow(new NoRelatedWarehousesToProduct());

        assertThrows(NoRelatedWarehousesToProduct.class, () -> {
            productService.getProductsByCategory(State.FF);
        });
    }


    //------------------------------------------TESTS METHOD GETPRODUCTS--------------------------------------------------

    @Test
    void testGetProducts() {
        when(productsRepository.count()).thenReturn(1L);
        when(productsRepository.findAll()).thenReturn(createProductList());

        List<Product> productList = productService.getProducts();
        assertEquals("1", productList.get(0).getProductId());
        assertEquals("2", productList.get(1).getProductId());
        assertEquals("product1", productList.get(0).getName());
        assertEquals("product2", productList.get(1).getName());
        assertEquals(State.FF, productList.get(0).getState());
        assertEquals(State.FF, productList.get(1).getState());
    }

    @Test
    void testProductsOutOfStockException() {
        when(productsRepository.count()).thenReturn(0L);

        assertThrows(ProductsOutOfStockException.class, () -> {
            productService.getProducts();
        });
    }

    //------------------------------------------TESTS METHOD GETORDERDETAILS--------------------------------------------------





    public List<Product> createProductList() {
        Product product = new Product();
        product.setName("product1");
        product.setProductId("1");
        product.setAdditionalInfo("infoAdd");
        product.setState(State.FF);
        Product product2 = new Product();
        product2.setName("product2");
        product2.setProductId("2");
        product2.setAdditionalInfo("infoAdd");
        product2.setState(State.FF);
        return List.of(product, product2);
    }

}

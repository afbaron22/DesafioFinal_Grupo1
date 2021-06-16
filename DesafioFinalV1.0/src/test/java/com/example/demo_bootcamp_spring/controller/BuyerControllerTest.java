package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.BatchStock;
import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.Batch.BatchService;
import com.example.demo_bootcamp_spring.services.Product.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "BUYER")
public class BuyerControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testGetProductsByCategory() throws Exception {

        when(productService.getProductsByCategory(any())).thenReturn(createProductList());
        this.mockMvc.perform(
                get("/api/v1/fresh-products/listOrder")
                        .param("productCategory","FF")).andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].productId").value("1"))
                        .andExpect(jsonPath("$[0].name").value("MANZANAS"))
                        .andExpect(jsonPath("$[0].additionalInfo").value("infoAdd"))
                        .andExpect(jsonPath("$[0].state").value("FF"));
    }
    @Test
    void testGetProductsByCategoryException() throws Exception {

        when(productService.getProductsByCategory(any())).thenThrow(new ProductsOutOfStockException("No products where found in the category: FS"));
        this.mockMvc.perform(
                get("/api/v1/fresh-products/listOrder")
                        .param("productCategory","FS")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No products where found in the category: FS"))
                .andExpect(jsonPath("$.code").value(400));
    }




    @Test
    void testGetProducts() throws Exception {

        when(productService.getProducts()).thenReturn(createProductList());
        this.mockMvc.perform(
                get("/api/v1/fresh-products/")
                        .content("")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("1"))
                .andExpect(jsonPath("$[0].name").value("MANZANAS"))
                .andExpect(jsonPath("$[0].additionalInfo").value("infoAdd"))
                .andExpect(jsonPath("$[0].state").value("FF"));
    }






    public List<Product> createProductList(){
        Product product = new Product();
            product.setName("MANZANAS");
        product.setProductId("1");
        product.setAdditionalInfo("infoAdd");
        product.setState(State.FF);
        Product product2 = new Product();
        product2.setName("product2");
        product2.setProductId("2");
        product2.setAdditionalInfo("infoAdd");
        product2.setState(State.FF);
        return List.of(product,product2);
    }

}

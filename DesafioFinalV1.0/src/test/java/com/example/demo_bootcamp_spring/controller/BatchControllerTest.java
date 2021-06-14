package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.SearchedWarehouseProducts;
import com.example.demo_bootcamp_spring.services.Batch.IBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BatchControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IBatchService batchService;

    @Test
    public void shouldGetProductsInWarehouse() throws Exception {
        List<Map<String,String>> warehouses = new ArrayList<>();
        Map<String,String> warehouse = new HashMap<>();
        warehouse.put("1","123");
        warehouses.add(warehouse);
        Integer querytype = 1;
        SearchedWarehouseProducts warehouseProducts = new SearchedWarehouseProducts();
        warehouseProducts.setWarehouses(warehouses);
        warehouseProducts.setProductId("1");
        when(batchService.getProductFromWarehouses("querytype")).thenReturn(warehouseProducts);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .param("querytype", String.valueOf(querytype)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
}

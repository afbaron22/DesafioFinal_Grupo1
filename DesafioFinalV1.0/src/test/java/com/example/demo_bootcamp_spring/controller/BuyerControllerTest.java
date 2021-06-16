package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.*;
import com.example.demo_bootcamp_spring.exceptions.OrderNotFoundException;
import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.OrdersService;
import com.example.demo_bootcamp_spring.services.Product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    @MockBean
    private OrdersService orderService;

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

    @Test
    public void shouldGetAListOfProducts() throws Exception {
        List<Product> productsResponse = getMockListOfProducts();

        when(productService.getProducts()).thenReturn(productsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(productsResponse)));
    }

    @Test
    public void shouldGetProductsByState() throws Exception {
        String productCategory = "FS";
        List<Product> productsResponse = getMockListOfProducts();

        when(productService.getProductsByCategory(State.FS)).thenReturn(productsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/listOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .param("productCategory", productCategory))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(productsResponse)));

    }

    @Test
    public void shouldRegisterPurchaseOrder() throws Exception {
        double totalResponse = 200.0;
        PurchaseOrderDTO purchaseOrder = getMockPurchaseOrder();

        when(orderService.registerOrder(purchaseOrder.getPurchaseOrder())).thenReturn(totalResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(purchaseOrder)))
                .andExpect(status().is2xxSuccessful())
                //.andExpect((jsonPath("totalResponse", is(totalResponse))));
                .andExpect(content().string(new ObjectMapper().writeValueAsString(totalResponse)));

    }

    @Test
    public void givenBadPurchaseOrderShouldReturnBadStatus() throws Exception {
        PurchaseOrderDTO purchaseOrder = getMockPurchaseOrder();

        when(orderService.registerOrder(purchaseOrder.getPurchaseOrder()))
                .thenThrow(ProductsOutOfStockException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(purchaseOrder)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUpdatePurchaseOrder() throws Exception {
        int orderId = 1;
        PurchaseOrderDTO purchaseOrder = getMockPurchaseOrder();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(purchaseOrder))
                .param("idOrder", String.valueOf(orderId)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldGetOrderDetail() throws Exception {
        int orderId = 1;
        OrderDetailDTO order = getOrderDetailMock(orderId);

        when(orderService.getOrderDetail(orderId)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("idOrder", String.valueOf(orderId)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(order)));
    }

    @Test
    public void givenBadOrderIdShouldReturnBadStatus() throws Exception {
        int orderId = 100;

        when(orderService.getOrderDetail(orderId)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("idOrder", String.valueOf(orderId)))
                .andExpect(status().is4xxClientError());
    }

    private List<Product> getMockListOfProducts() {
        List<Product> products = new ArrayList<>();
        Product productOne = new Product();
        productOne.setProductId("1");
        productOne.setPrice(1.0);
        productOne.setAdditionalInfo("lorem ipsum");
        productOne.setName("product one");
        return products;
    }

    private OrderDetailDTO getOrderDetailMock(int id) {
        OrderDetailDTO orderDetail = new OrderDetailDTO();
        orderDetail.setOrderProducts(new ArrayList<>());
        orderDetail.setOrderId(id);
        return orderDetail;
    }

    private PurchaseOrderDTO getMockPurchaseOrder() {
        OrderDTO order = new OrderDTO();

        List<ProductDTO> products = getMockListOfProducts().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setQuantity(1);
            return productDTO;
        }).collect(Collectors.toList());

        order.setBuyerId("1");
        order.setProducts(products);

        PurchaseOrderDTO purchaseOrder = new PurchaseOrderDTO();
        purchaseOrder.setPurchaseOrder(order);
        return purchaseOrder;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

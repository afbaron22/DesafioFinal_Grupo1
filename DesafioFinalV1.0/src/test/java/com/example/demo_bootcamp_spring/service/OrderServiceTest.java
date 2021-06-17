package com.example.demo_bootcamp_spring.service;

import com.example.demo_bootcamp_spring.dtos.OrderDTO;
import com.example.demo_bootcamp_spring.models.*;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.OrdersRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.services.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.example.demo_bootcamp_spring.models.State.FF;

@SpringBootTest
public class OrderServiceTest {

    private OrderDTO shoudPassOrder;
    private OrderDTO shoudRejectOrder;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    private Product product6;
    private List<Product> productRepoMOCK;
    private Batch batch1;
    private Batch batch2;
    private Batch batch3;
    private Batch batch4;
    private Batch batch5;
    private Batch batch6;
    private List<Batch> batchRepoMOCK;

    private InboundOrder inboundOrderFF;
    private InboundOrder inboundOrderFS;
    private InboundOrder inboundOrderRF;

    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private OrdersService ordersService;

    @BeforeEach
    void setup(){

        product1 = new Product("1", "Bananas","Banaas Bananón", State.FS, 29.99);
        product2 = new Product("2", "Peras","Peras frescas", State.FS, 40.23);
        product3 = new Product("3", "Merluza","Merluza fresca empanada", FF, 100.75);
        product4 = new Product("4", "Calamares","Calamares congelados separados por kilo", FF, 123.99);
        product5 = new Product("5", "Leche","Leche parcialmente descremada Sancor", State.RF, 20.00);
        product6 = new Product("6", "Yogurt","Yogurt griego La Serenísima", State.RF, 25.25);

        inboundOrderFF = new InboundOrder();
        inboundOrderFS = new InboundOrder();
        inboundOrderRF = new InboundOrder();

        batch1 = new Batch("1",product1, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,5,inboundOrderFS);
        batch2 = new Batch("2",product1, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,5,inboundOrderFS);
        batch3 = new Batch("3",product3, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,0,inboundOrderFF);
        batch4 = new Batch("4",product4, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,123,inboundOrderFF);
        batch5 = new Batch("5",product5, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,123,inboundOrderRF);
        batch6 = new Batch("6",product5, 12.0f, 20.00f, LocalDate.now(),LocalDate.now(), LocalDateTime.now(),123,123,inboundOrderRF);

        productRepoMOCK = Arrays.asList(product1,product2,product3,product4,product5, product6);
        batchRepoMOCK = Arrays.asList(batch1,batch2,batch3,batch4,batch5,batch6);
    }


    @Test
    public void shouldGetAllProducts() {
    }
}

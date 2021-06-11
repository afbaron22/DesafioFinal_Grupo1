package com.example.demo_bootcamp_spring.services;

import com.example.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.example.demo_bootcamp_spring.repository.OrdersRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import com.example.demo_bootcamp_spring.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    //Cache cache = new Cache<List<Turno>>(5);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate date = LocalDate.now();

    public void saveBatch(InboundOrderDTO inboundOrderDTO){

       /* @NotNull(message = "Order Number is required")
        private Integer orderNumber;
        @NotNull(message = "Order Date  is required")
        private LocalDate orderDate;
        @Valid
        private SectionDTO section;
        @Valid
        private List<BatchDTO> batchStock;*/
    }

}

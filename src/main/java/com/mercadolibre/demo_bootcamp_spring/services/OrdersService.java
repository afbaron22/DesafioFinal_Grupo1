package com.mercadolibre.demo_bootcamp_spring.services;

import com.mercadolibre.demo_bootcamp_spring.dtos.BatchDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.SectionDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.repository.OrdersRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrdersService implements IOrderService{
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

    //TODO implementar logica del servicio

    @Override
    public void registerOrder(OrderDTO orderDTO) {

    }

    @Override
    public List<Product> getOrderDetail(Integer idOrder) {
        return null;
    }

    @Override
    public void updateOrder(Integer idOrder, OrderDTO orderDTO) {

    }
}

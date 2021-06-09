package com.mercadolibre.demo_bootcamp_spring.services;

import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Orders;
import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.repository.OrdersRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //TODO implementar logica del servicio

    @Override
    public Integer registerOrder(OrderDTO orderDTO) {
        //Dar de alta una orden con la lista de productos que componen la
        //PurchaseOrder. Calcular el precio final, y devolverlo junto a un status
        //code “201 CREATED”. Si no hay stock de un producto
        //notificar la situación devolviendo un error por producto, no a nivel de orden.


        //Recorrer todos los productos si no hay stock
        //verificar vencimiento del producto no sea inferior a 3 semanas
        //si agrego producto tengo que restar cantidad del stock actual
        //para saber el stock y la fecha de vencimiento tengo que buscar los batchs relacionados a ese producto
        //Calcular precio final

        //TODO incompleto

        Orders newOrder = orderDTOtoOrder(orderDTO);
        newOrder = ordersRepository.save(newOrder);
        return null;
    }

    @Override
    public List<Product> getOrderDetail(Integer idOrder) {
        return null;
    }

    @Override
    public void updateOrder(Integer idOrder, OrderDTO orderDTO) {

    }


    private Orders orderDTOtoOrder(OrderDTO orderDTO){
        //TODO hacer la lógica bien
        return new Orders();
    }
}

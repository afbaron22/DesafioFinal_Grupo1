package com.example.demo_bootcamp_spring.services;

import com.example.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.example.demo_bootcamp_spring.dtos.OrderDTO;
import com.example.demo_bootcamp_spring.dtos.OrderDetailDTO;
import com.example.demo_bootcamp_spring.dtos.OrderProductDetailDTO;
import com.example.demo_bootcamp_spring.exceptions.OrderNotFoundException;
import com.example.demo_bootcamp_spring.exceptions.ProductsOutOfStockException;
import com.example.demo_bootcamp_spring.models.Batch;
import com.example.demo_bootcamp_spring.models.OrderProduct;
import com.example.demo_bootcamp_spring.models.Orders;
import com.example.demo_bootcamp_spring.models.Product;
import com.example.demo_bootcamp_spring.repository.BatchRepository;
import com.example.demo_bootcamp_spring.repository.OrdersRepository;
import com.example.demo_bootcamp_spring.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersService implements IOrderService {

    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;
    private BatchRepository batchRepository;

    public OrdersService(OrdersRepository ordersRepository, ProductsRepository productsRepository, BatchRepository batchRepository) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    //Dar de alta una orden con la lista de productos que componen la
    //PurchaseOrder. Calcular el precio final, y devolverlo junto a un status
    //code “201 CREATED”. Si no hay stock de un producto
    //notificar la situación devolviendo un error por producto, no a nivel de orden.

    //Recorrer todos los productos si no hay stock
    //verificar vencimiento del producto no sea inferior a 3 semanas
    //si agrego producto tengo que restar cantidad del stock actual
    //para saber el stock y la fecha de vencimiento tengo que buscar los batchs relacionados a ese producto
    //Calcular precio final
    public Double registerOrder(OrderDTO orderDTO ) throws ProductsOutOfStockException {

        List<OrderProduct> orderProductList = checkAndGetOrderProduct(orderDTO);
        Double finalPrice = 0.0;

        for(OrderProduct op : orderProductList){
            finalPrice += op.getProduct().getPrice() * op.getQuantity();
        }

        Orders newOrder = new Orders();
        newOrder.setUser(orderDTO.getBuyerId());
        newOrder.setOrderProducts(new HashSet<>(orderProductList));
        newOrder.setCreatedAt(orderDTO.getDate().toString());

        for(OrderProduct orderProduct : orderProductList){
            orderProduct.setOrders(newOrder);
        }
        newOrder = ordersRepository.save(newOrder);

        return finalPrice;
    }

    @Override
    public OrderDetailDTO getOrderDetail(Integer idOrder)   {

       OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        List<OrderProductDetailDTO> orderProductDetailList = new ArrayList<>();
        Optional<List<OrderProductDetailDTO>> optionalOrderProductDetailList = ordersRepository.getProductsInOrder(idOrder);
        if (optionalOrderProductDetailList.isEmpty()){
            throw new OrderNotFoundException(idOrder);
        }
        orderProductDetailList = optionalOrderProductDetailList.get();
        orderDetailDTO.setOrderId(idOrder);
        orderDetailDTO.setOrderProducts(orderProductDetailList);
        return orderDetailDTO;
    }

    @Override
    public void updateOrder(Integer idOrder, OrderDTO orderDTO) {
        //Modificar orden existente. que sea de tipo carrito para modificar

        List<OrderProduct> orderProductList = checkAndGetOrderProduct(orderDTO);

        Optional<Orders> ordersOptional = ordersRepository.findById(idOrder);
        if (ordersOptional.isEmpty()){
            throw new OrderNotFoundException(idOrder);
        }
        orderProductList = orderProductList
                .stream()
                .peek(orderProduct -> orderProduct.setOrders(ordersOptional.get()))
                .collect(Collectors.toList());

        Orders orderToUpdate = new Orders();
        orderToUpdate = ordersOptional.get();
        orderToUpdate.getOrderProducts().clear();
        orderToUpdate.getOrderProducts().addAll(new HashSet(orderProductList));
        //orderToUpdate.setOrderProducts();
        orderToUpdate = ordersRepository.save(orderToUpdate);

    }

    private int productStock(String prodId, LocalDate dueDate){
        List<Batch> batchList = batchRepository.findByProductIdAndDueDate(prodId, dueDate);
        if (batchList.isEmpty()){
            return 0;
        }
        return batchList.stream()
                .reduce(0,(subtotal, batch) -> subtotal + batch.getCurrentQuantity(), Integer::sum);
    }

    public List<OrderProduct> checkAndGetOrderProduct(OrderDTO orderDTO){
        List<Double> prices = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<OrderProduct> orderProductList = new ArrayList<>();
        int numberOfDays = 21;
        LocalDate date = LocalDate.now().plusDays(numberOfDays);

        orderDTO.getProducts().forEach(orderProduct -> {
            int stockAvailable = productStock(orderProduct.getProductId(), date);
            if (stockAvailable >= orderProduct.getQuantity()){

                Product repoProduct = productsRepository.findById(orderProduct.getProductId()).orElseThrow();
                OrderProduct orderProduct1 = new OrderProduct();
                orderProduct1.setProduct(repoProduct);
                orderProduct1.setQuantity(orderProduct.getQuantity());
                orderProductList.add(orderProduct1);

                prices.add(repoProduct.getPrice() * orderProduct.getQuantity());
            } else {
                errors.add(orderProduct.getProductId() + " has " + stockAvailable + " items available");
            }
        });
        if (errors.size() > 0) {
            String errorProducts = errors.stream().reduce("", (acum, error) -> acum + error + "\n");
            throw new ProductsOutOfStockException("the following products are not available: \n" + errorProducts);

        } return orderProductList;
    }

}

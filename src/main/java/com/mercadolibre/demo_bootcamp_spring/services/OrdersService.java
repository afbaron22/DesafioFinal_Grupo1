package com.mercadolibre.demo_bootcamp_spring.services;

import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.Batch;
import com.mercadolibre.demo_bootcamp_spring.models.Orders;
import com.mercadolibre.demo_bootcamp_spring.models.Product;
import com.mercadolibre.demo_bootcamp_spring.repository.BatchRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.OrdersRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.ProductsRepository;
import com.mercadolibre.demo_bootcamp_spring.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService implements IOrderService{

    public OrdersRepository ordersRepository;
    public ProductsRepository productsRepository;
    public BatchRepository batchRepository;

    public OrdersService(OrdersRepository ordersRepository, ProductsRepository productsRepository, BatchRepository batchRepository) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.batchRepository = batchRepository;
    }
//TODO implementar logica del servicio

    @Override
    public Double registerOrder(OrderDTO orderDTO) {
        //Dar de alta una orden con la lista de productos que componen la
        //PurchaseOrder. Calcular el precio final, y devolverlo junto a un status
        //code “201 CREATED”. Si no hay stock de un producto
        //notificar la situación devolviendo un error por producto, no a nivel de orden.


        //Recorrer todos los productos si no hay stock
        //verificar vencimiento del producto no sea inferior a 3 semanas
        //si agrego producto tengo que restar cantidad del stock actual
        //para saber el stock y la fecha de vencimiento tengo que buscar los batchs relacionados a ese producto
        //Calcular precio final

        //TODO chequear que exista cantidad suficiente de cada producto, en caso negativo devolver lista de errores.
        List<Double> prices = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Date date = new Date();
        orderDTO.getProducts().stream().forEach(orderProduct -> {
           Product repoProduct = productsRepository.findById(orderProduct.getProductId()).orElseThrow();
           products.add(repoProduct);
           //TODO usar checkProductAvailability para comrpobar que haya suficiente producto
         /*  if (checkProductAvailability(orderProduct.getProductId(),,orderProduct.getQuantity())){

           };*/
           prices.add(repoProduct.getPrice() * orderProduct.getQuantity());
        });
        Double finalPrice = prices.stream().reduce(0.0, Double::sum);
        //TODO ver de cambiar nombre de la clase por purchaseOrder
        Orders newOrder = new Orders();
        newOrder.setUser(orderDTO.getBuyerId());
        newOrder.setProducts(products);
        newOrder.setCreatedAt(orderDTO.getDate().toString());
        return finalPrice;
    }

    @Override
    public List<Product> getOrderDetail(Integer idOrder) {
        //Mostrar productos en la orden.
        return null;
    }

    @Override
    public void updateOrder(Integer idOrder, OrderDTO orderDTO) {
        //Modificar orden existente. que sea de tipo carrito para modificar

    }

    private boolean checkProductAvailability(String prodId, Date dueDate, Integer purchaseQuantity){
        List<Batch> batchList = new ArrayList<>();
        batchList = batchRepository.findByProductIdAndDueDate(prodId,dueDate);
        if (batchList.size() == 0){
            return false;
        }
        int availableQuantity = batchList
                .stream().reduce(0,(subtotal, batch) -> subtotal + batch.getCurrentQuantity(), Integer::sum);
        return  availableQuantity >= purchaseQuantity;
    }

    private Orders orderDTOtoOrder(OrderDTO orderDTO){
        //TODO hacer la lógica bien
        return new Orders();
    }
}

package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.OrderDTO;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.IOrderService;
import com.example.demo_bootcamp_spring.services.Product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1")
public class BuyerController {

    private final IProductService productService;
    private final IOrderService orderService;

    public BuyerController(IProductService productService, IOrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //TODO validaciones
    //TODO verificacion que el usuario logueado es un comprador (BUYER)

    @GetMapping("fresh-products")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("fresh-products/listOrder")
    public ResponseEntity<?> getProductsByCategory(@Valid @RequestParam State productCategory){
        return new ResponseEntity(productService.getProductsByCategory(productCategory), HttpStatus.OK);
    }

    @PostMapping("fresh-products/orders")
    public ResponseEntity<?> registerOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity(orderService.registerOrder(orderDTO), HttpStatus.OK);
    }

    @GetMapping("fresh-products/orders")
    public ResponseEntity<?> getOrderDetail(@Valid @RequestParam Integer idOrder){
        return new ResponseEntity(orderService.getOrderDetail(idOrder), HttpStatus.OK);
    }

    @PutMapping("fresh-products/orders")
    public ResponseEntity<?> updateOrder(@Valid @RequestParam Integer idOrder, @RequestBody OrderDTO orderDTO){
        orderService.updateOrder(idOrder, orderDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}

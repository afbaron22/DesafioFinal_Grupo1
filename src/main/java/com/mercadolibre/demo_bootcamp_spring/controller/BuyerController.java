package com.mercadolibre.demo_bootcamp_spring.controller;

import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.models.State;
import com.mercadolibre.demo_bootcamp_spring.services.IOrderService;
import com.mercadolibre.demo_bootcamp_spring.services.Product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BuyerController {

    private IProductService productService;
    private IOrderService orderService;

    public BuyerController(IProductService productService, IOrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //TODO validaciones
    //TODO verificacion que el usuario logueado es un comprador (BUYER)

    @GetMapping("")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity(productService.getProducts(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getProductsByCategory(@Valid @RequestParam State productCategory){
        return new ResponseEntity(productService.getProductsByCategory(productCategory), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> registerOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity(orderService.registerOrder(orderDTO),HttpStatus.OK);
    }
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderDetail(@Valid @RequestParam Integer idOrder){
        return new ResponseEntity(orderService.getOrderDetail(idOrder), HttpStatus.OK);
    }
    @PutMapping("/orders")
    public ResponseEntity<?> updateOrder(@Valid @RequestParam Integer idOrder, @RequestBody OrderDTO orderDTO){
        orderService.updateOrder(idOrder, orderDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}

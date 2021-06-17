package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.OrderDTO;
import com.example.demo_bootcamp_spring.dtos.PurchaseOrderDTO;
import com.example.demo_bootcamp_spring.models.OrderProduct;
import com.example.demo_bootcamp_spring.models.State;
import com.example.demo_bootcamp_spring.services.IOrderService;
import com.example.demo_bootcamp_spring.services.Product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BuyerController {

    private final IProductService productService;
    private final IOrderService orderService;

    public BuyerController(IProductService productService, IOrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //TODO validaciones

    @GetMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/listOrder")
    public ResponseEntity<?> getProductsByCategory(@Valid @RequestParam State productCategory){
        return new ResponseEntity(productService.getProductsByCategory(productCategory), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> registerOrder(@Valid @RequestBody PurchaseOrderDTO purchaseOrder){
        return new ResponseEntity(orderService.registerOrder(purchaseOrder.getPurchaseOrder()), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderDetail(@Valid @RequestParam Integer idOrder){
        return new ResponseEntity(orderService.getOrderDetail(idOrder), HttpStatus.OK);
    }

    @PutMapping("/orders")
    public ResponseEntity<?> updateOrder(@Valid @RequestParam Integer idOrder, @RequestBody PurchaseOrderDTO purchaseOrder){
        orderService.updateOrder(idOrder, purchaseOrder.getPurchaseOrder());
        return new ResponseEntity(HttpStatus.OK);
    }
}

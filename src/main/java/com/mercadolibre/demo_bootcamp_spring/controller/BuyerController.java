package com.mercadolibre.demo_bootcamp_spring.controller;

import com.mercadolibre.demo_bootcamp_spring.dtos.OrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.SampleDTO;
import com.mercadolibre.demo_bootcamp_spring.models.State;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BuyerController {

    //TODO completar logica de los endpoints

    @GetMapping("")
    public ResponseEntity<?> getProducts(){
        return null;
    }
    @GetMapping("/list")
    public ResponseEntity<?> getProductsByCategory(@Valid @RequestParam State productCategory){
        return null;
    }

    @PostMapping("orders")
    public ResponseEntity<?> registerOrder(@Valid @RequestBody OrderDTO orderDTO){
        return null;
    }
    @GetMapping("orders")
    public ResponseEntity<?> getOrderDetail(@Valid @RequestParam Integer idOrder){
        return null;
    }
    @PutMapping("orders")
    public ResponseEntity<?> updateOrder(@Valid @RequestParam Integer idOrder, @RequestBody OrderDTO orderDTO){
        return null;
    }
}

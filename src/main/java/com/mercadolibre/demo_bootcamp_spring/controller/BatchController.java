package com.mercadolibre.demo_bootcamp_spring.controller;


import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderTransaction;
import com.mercadolibre.demo_bootcamp_spring.dtos.SampleDTO;
import com.mercadolibre.demo_bootcamp_spring.exceptions.ValidationErrorException;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BatchController {

    IBatchService batchService;
    public BatchController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/inboundorder")
    public ResponseEntity<?> insertBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult.getFieldError().getField(),bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity(batchService.saveBatch(inboundOrder), HttpStatus.CREATED);
    }
    @PutMapping("/inboundorder")
    public ResponseEntity<?> putBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult.getFieldError().getField(),bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity(batchService.putBatch(inboundOrder), HttpStatus.CREATED);
    }
    @GetMapping(path = "/list")
    public ResponseEntity<?> getProduct(@RequestParam Integer querytype,@RequestParam(required = false) String ordBy){

        return new ResponseEntity(batchService.getProductFromBatches(String.valueOf(querytype), ordBy), HttpStatus.CREATED);
    }
    @GetMapping(path = "/list/hila")
    public ResponseEntity<?> getProductsInWarehouse(@RequestParam Integer querytype){

        return new ResponseEntity(batchService.getProductFromWarehouses(String.valueOf(querytype)), HttpStatus.CREATED);
    }

}


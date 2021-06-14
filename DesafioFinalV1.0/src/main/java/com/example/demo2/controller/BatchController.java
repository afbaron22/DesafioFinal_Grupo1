package com.example.demo2.controller;

import com.example.demo2.dtos.InboundOrderTransaction;
import com.example.demo2.services.Batch.IBatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> insertBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder) {

        return new ResponseEntity(batchService.saveBatch(inboundOrder), HttpStatus.CREATED);
    }

    @PutMapping("/inboundorder")
    public ResponseEntity<?> putBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder) {
        return new ResponseEntity(batchService.putBatch(inboundOrder), HttpStatus.CREATED);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getProduct(@RequestParam Integer querytype, @RequestParam(required = false) String ordBy) {

        return new ResponseEntity(batchService.getProductFromBatches(String.valueOf(querytype), ordBy), HttpStatus.CREATED);
    }

    @GetMapping(path = "/warehouse")
    public ResponseEntity<?> getProductsInWarehouse(@RequestParam Integer querytype) {

        return new ResponseEntity(batchService.getProductFromWarehouses(String.valueOf(querytype)), HttpStatus.CREATED);
    }
}


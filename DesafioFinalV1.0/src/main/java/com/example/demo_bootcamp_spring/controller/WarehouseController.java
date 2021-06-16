package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.services.Batch.IBatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class WarehouseController {


    IBatchService batchService;
    public WarehouseController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping(path = "/due-date")
    public ResponseEntity<?> getBatchesInWarehouseByDueDate(@RequestParam int days){
        //TODO get idWarehouse from Employee
        Integer idWarehouse = 1;
        return new ResponseEntity(batchService.getBatchesInWarehouseByDueDate(idWarehouse,days), HttpStatus.CREATED);
    }


}
package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.exceptions.NotAuthorizedUser;
import com.example.demo_bootcamp_spring.services.Batch.IBatchService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class WarehouseController {


    IBatchService batchService;
    JwtTokenUtil jwtTokenUtil;
    public WarehouseController(IBatchService batchService,JwtTokenUtil jwtTokenUtil) {
        this.batchService = batchService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping(path = "/due-date")
    public ResponseEntity<?> getBatchesInWarehouseByDueDate(@RequestParam int days,@RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);
        var idWarehouse = batchService.validate(jwtTokenUtil.getUsernameFromToken(jwtToken));
        return new ResponseEntity(batchService.getBatchesInWarehouseByDueDate(idWarehouse,days), HttpStatus.CREATED);
    }


}
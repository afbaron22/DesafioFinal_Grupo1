package com.example.demo_bootcamp_spring.controller;
import com.example.demo_bootcamp_spring.dtos.InboundOrderTransaction;
import com.example.demo_bootcamp_spring.services.Batch.IBatchService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BatchController {

    IBatchService batchService;
    JwtTokenUtil jwtTokenUtil;
    public BatchController(IBatchService batchService, JwtTokenUtil jwtTokenUtil ) {
        this.batchService = batchService;
        this.jwtTokenUtil= jwtTokenUtil;
    }

    @PostMapping("/inboundorder")
    public ResponseEntity<?> insertBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder, @RequestHeader("Authorization") String token ) throws Exception{

    String jwtToken = token.substring(7);
    String userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return new ResponseEntity(batchService.saveBatch(inboundOrder), HttpStatus.CREATED);
    }

    @PutMapping("/inboundorder")
    public ResponseEntity<?> putBatch(@Valid @RequestBody InboundOrderTransaction inboundOrder){

        return new ResponseEntity(batchService.putBatch(inboundOrder), HttpStatus.CREATED);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getProduct(@RequestParam Integer querytype, @RequestParam(required = false) String ordBy){
        return new ResponseEntity(batchService.getProductFromBatches(String.valueOf(querytype), ordBy), HttpStatus.CREATED);
    }

    @GetMapping(path = "/warehouse")
    public ResponseEntity<?> getProductsInWarehouse(@RequestParam Integer querytype){
        return new ResponseEntity(batchService.getProductFromWarehouses(String.valueOf(querytype)), HttpStatus.CREATED);
    }

}


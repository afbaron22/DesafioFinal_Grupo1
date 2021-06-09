package com.mercadolibre.demo_bootcamp_spring.controller;

import com.mercadolibre.demo_bootcamp_spring.dtos.InboundOrderDTO;
import com.mercadolibre.demo_bootcamp_spring.dtos.SampleDTO;
import com.mercadolibre.demo_bootcamp_spring.exceptions.ValidationErrorException;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class BatchController {

    IBatchService batchService;

    public BatchController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/inboundorder")
    public ResponseEntity<?> getScore(@Valid @RequestBody InboundOrderDTO inboundOrder, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult.getFieldError().getField(),bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity(batchService.saveBatch(inboundOrder), HttpStatus.OK);
    }
}
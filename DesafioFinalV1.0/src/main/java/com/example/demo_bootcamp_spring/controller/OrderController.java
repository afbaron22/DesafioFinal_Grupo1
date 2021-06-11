package com.example.demo_bootcamp_spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/fresh-products")
public class OrderController {
  /*  @PostMapping("/inboundorder")
    public ResponseEntity<?> releaseBatch(@Valid @RequestBody InboundOrderDTO inboundOrderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationErrorException(bindingResult.getFieldError().getField(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity(service.getCertificado(estudiante), HttpStatus.OK);
    }*/
}

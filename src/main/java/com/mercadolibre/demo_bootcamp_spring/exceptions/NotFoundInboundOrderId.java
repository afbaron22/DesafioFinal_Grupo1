package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class NotFoundInboundOrderId extends RuntimeException{
    public NotFoundInboundOrderId() {
        super("The given Inbound orderid does not exist.Try again.");
    }
}

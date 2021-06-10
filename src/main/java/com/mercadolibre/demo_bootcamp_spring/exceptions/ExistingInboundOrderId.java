package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class ExistingInboundOrderId extends RuntimeException {
    public ExistingInboundOrderId() {
        super("The given InboundOrder Id already exist.Create a new one!");
    }
}

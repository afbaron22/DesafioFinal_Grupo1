package com.example.demo2.exceptions;

public class ExistingInboundOrderId extends RuntimeException {
    public ExistingInboundOrderId() {
        super("The given InboundOrder Id already exist.Create a new one!");
    }
}

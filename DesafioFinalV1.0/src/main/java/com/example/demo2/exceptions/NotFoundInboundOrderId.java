package com.example.demo2.exceptions;

public class NotFoundInboundOrderId extends RuntimeException{
    public NotFoundInboundOrderId() {
        super("The given Inbound orderid does not exist.Try again.");
    }
}

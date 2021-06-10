package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class BatchNotFoundException extends RuntimeException{
    public BatchNotFoundException(String message) {
        super(message);
    }
}

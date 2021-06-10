package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class ProductsOutOfStockException extends RuntimeException{
    public ProductsOutOfStockException(String message) {
        super(message);
    }
}

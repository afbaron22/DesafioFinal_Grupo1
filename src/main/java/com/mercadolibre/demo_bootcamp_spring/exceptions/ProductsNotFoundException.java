package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class ProductsNotFoundException extends RuntimeException{
    public ProductsNotFoundException(String message) {
        super(message);
    }
}

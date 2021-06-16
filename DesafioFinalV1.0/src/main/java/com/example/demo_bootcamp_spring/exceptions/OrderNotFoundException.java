package com.example.demo_bootcamp_spring.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Integer idOrder) {
        super(idOrder + ": order not exist");
    }
}

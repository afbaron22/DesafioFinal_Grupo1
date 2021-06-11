package com.example.demo_bootcamp_spring.exceptions;

public class NoRelatedWarehousesToProduct extends RuntimeException{
    public NoRelatedWarehousesToProduct() {
        super("No warehouses related to this product.");
    }
}

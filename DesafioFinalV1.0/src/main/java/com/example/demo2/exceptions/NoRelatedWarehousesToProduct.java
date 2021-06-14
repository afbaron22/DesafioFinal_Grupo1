package com.example.demo2.exceptions;

public class NoRelatedWarehousesToProduct extends RuntimeException{
    public NoRelatedWarehousesToProduct() {
        super("No warehouses related to this product.");
    }
}

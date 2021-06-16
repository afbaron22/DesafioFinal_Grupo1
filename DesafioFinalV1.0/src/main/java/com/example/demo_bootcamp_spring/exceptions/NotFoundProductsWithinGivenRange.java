package com.example.demo_bootcamp_spring.exceptions;

public class NotFoundProductsWithinGivenRange extends RuntimeException {
    public NotFoundProductsWithinGivenRange() {
        super("Not found products with the given time range");
    }

}

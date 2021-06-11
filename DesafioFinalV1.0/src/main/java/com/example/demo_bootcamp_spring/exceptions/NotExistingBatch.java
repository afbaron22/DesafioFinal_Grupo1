package com.example.demo_bootcamp_spring.exceptions;

public class NotExistingBatch extends RuntimeException{
    public NotExistingBatch() {
        super("Not existent batch");
    }
}
